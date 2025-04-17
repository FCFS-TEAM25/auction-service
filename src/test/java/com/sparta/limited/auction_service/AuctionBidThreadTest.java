package com.sparta.limited.auction_service;

import com.sparta.limited.auction_service.auction.application.dto.request.AuctionCreateBidRequest;
import com.sparta.limited.auction_service.auction.application.service.AuctionService;
import com.sparta.limited.auction_service.auction.domain.model.Auction;
import com.sparta.limited.auction_service.auction.domain.repository.AuctionRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class AuctionBidThreadTest {

    private static final Logger logger = LoggerFactory.getLogger(AuctionBidThreadTest.class);

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private UUID auctionId;
    private final BigDecimal startingBid = BigDecimal.valueOf(10000L);

    @BeforeEach
    @Transactional
    void setUpAuction() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = now.plusMinutes(10);
        LocalDateTime endTime = now.plusHours(1);

        Auction auction = Auction.of(UUID.randomUUID(), startingBid, startTime, endTime);
        auctionRepository.save(auction);
        auctionId = auction.getId();

		jdbcTemplate.update(
			"UPDATE p_auction SET start_time = ?, status = 'ACTIVE' WHERE id = ?",
			java.sql.Timestamp.valueOf(now.minusMinutes(10)),
			auctionId
		);
    }

    @Test
    @DisplayName("경매 입찰 동시성 테스트")
    public void auctionBidTest() throws InterruptedException {
        int numThreads = 4000;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);
        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();
        long testStartTime = System.currentTimeMillis();

        for (int i = 0; i < numThreads; i++) {
            final Long userId = i + 1L;
            final BigDecimal bid = startingBid.add(BigDecimal.valueOf(1000));

            executorService.submit(() -> {
                try {
                    AuctionCreateBidRequest request = new AuctionCreateBidRequest(bid);
                    auctionService.createAuctionBid(auctionId, userId, request);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    logger.error(
                        "입찰 처리 중 오류 발생: " + e.getMessage() + ", userId: " + userId + ", bid: "
                            + bid, e);
                    failCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        long testFinishTime = System.currentTimeMillis();
        long totalTime = testFinishTime - testStartTime;

        logger.info("==== 경매 입찰 성능 테스트 결과 ====");
        logger.info("테스트 소요 시간: " + totalTime + "ms");
        logger.info("요청당 평균 처리 시간: " + (totalTime / (double) numThreads) + "ms");
        logger.info("초당 처리 요청 수(TPS): " + (numThreads * 1000.0 / totalTime));
        logger.info("성공한 입찰 수: " + successCount.get());
        logger.info("실패한 입찰 수: " + failCount.get());
        logger.info("성공률: " + (successCount.get() * 100.0 / numThreads) + "%");

        assertTrue(successCount.get() + failCount.get() == numThreads,
            "모든 요청이 처리되었는지 확인");
    }


}
