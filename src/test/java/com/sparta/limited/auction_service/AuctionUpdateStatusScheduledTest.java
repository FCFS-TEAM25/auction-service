package com.sparta.limited.auction_service;

import com.sparta.limited.auction_service.auction.application.service.scheduler.AuctionScheduler;
import com.sparta.limited.auction_service.auction.domain.model.Auction;
import com.sparta.limited.auction_service.auction.domain.model.AuctionStatus;
import com.sparta.limited.auction_service.auction.domain.repository.AuctionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class AuctionUpdateStatusScheduledTest {

	private static final Logger logger = LoggerFactory.getLogger(AuctionUpdateStatusScheduledTest.class);

	@Autowired
	private AuctionScheduler auctionScheduler;

	@Autowired
	private AuctionRepository auctionRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	@Transactional
	public void testOptimisticLocking() throws InterruptedException {

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime startTime = now.plusMinutes(10);
		LocalDateTime endTime = now.plusHours(1);

		logger.info("경매 생성");
		Auction auction = Auction.of(UUID.randomUUID(), BigDecimal.valueOf(50000), startTime, endTime);
		auctionRepository.save(auction);

		Auction savedAuction = auctionRepository.findById(auction.getId());
		Integer initialVersion = savedAuction.getVersion();
		logger.info("초기 버전: {}", initialVersion);

		// 유효성 검증으로 인한 새 경매 객체 생성(같은 경매 ID로 과거 시간으로 변경)
		entityManager.createQuery(
				"UPDATE Auction a SET a.startTime = :newStartTime WHERE a.id = :id")
			.setParameter("newStartTime", now.minusMinutes(10))
			.setParameter("id", auction.getId())
			.executeUpdate();

		auctionScheduler.updateStatusScheduled();

		Auction updatedAuction = auctionRepository.findById(auction.getId());
		Integer updatedVersion = updatedAuction.getVersion();
		logger.info("업데이트 후 버전: {}", updatedVersion);
		logger.info("업데이트 후 경매 상태: {}", updatedAuction.getStatus());

		// 실패 시 출력
		assertEquals(AuctionStatus.ACTIVE, updatedAuction.getStatus(), "Error : 경매 상태가 ACTIVE로 변경되어야 합니다.");
		assertTrue(updatedVersion > initialVersion, "Error : 낙관적 락으로 인해 버전이 증가해야 합니다.");
	}

	@Test
	@Transactional
	public void testOptimisticLockingRetry() {
		logger.info("***낙관적 락 충돌 버전***");
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime startTime = now.plusMinutes(10);
		LocalDateTime endTime = now.plusHours(1);

		logger.info("경매 생성");
		Auction auction = Auction.of(UUID.randomUUID(), BigDecimal.valueOf(50000), startTime, endTime);
		auctionRepository.save(auction);

		Auction savedAuction = auctionRepository.findById(auction.getId());
		Integer initialVersion = savedAuction.getVersion();
		logger.info("초기 버전: {}", initialVersion);

		// 유효성 검증으로 인한 새 경매 객체 생성(같은 경매 ID로 과거 시간으로 변경)
		entityManager.createQuery(
				"UPDATE Auction a SET a.startTime = :newStartTime WHERE a.id = :id")
			.setParameter("newStartTime", now.minusMinutes(10))
			.setParameter("id", auction.getId())
			.executeUpdate();

		// 의도적으로 버전 충돌 생성
		entityManager.createQuery(
				"UPDATE Auction a SET a.version = a.version + 10 WHERE a.id = :id")
			.setParameter("id", auction.getId())
			.executeUpdate();

		logger.info("스케줄러 실행");
		try {
			auctionScheduler.updateStatusScheduled();
		} catch (Exception e) {
			logger.error("스케줄러 실행 중 예외 발생", e);
		}

		entityManager.clear();

		Auction updatedAuction = auctionRepository.findById(auction.getId());
		Integer updatedVersion = updatedAuction.getVersion();
		logger.info("업데이트 후 버전: {}", updatedVersion);
		logger.info("업데이트 후 경매 상태: {}", updatedAuction.getStatus());

		//실패 시 출력
		assertEquals(AuctionStatus.PENDING, updatedAuction.getStatus(),"Error : 낙관적 락 충돌로 인해 상태가 변경되지 않아야 합니다. -> PENDING 으로");
		assertEquals(initialVersion + 10, updatedAuction.getVersion().intValue(), "Error : 버전은 의도적으로 변경한 값으로 유지되어야 합니다. -> +10 으로");
	}

}
