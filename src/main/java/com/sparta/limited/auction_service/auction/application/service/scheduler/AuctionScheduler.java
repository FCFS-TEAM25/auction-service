package com.sparta.limited.auction_service.auction.application.service.scheduler;

import com.sparta.limited.auction_service.auction.application.service.dto.MaxBidData;
import com.sparta.limited.auction_service.auction.domain.model.Auction;
import com.sparta.limited.auction_service.auction.domain.model.AuctionStatus;
import com.sparta.limited.auction_service.auction.domain.repository.AuctionRepository;
import com.sparta.limited.auction_service.auction.infrastructure.redis.RedisFacade;
import jakarta.persistence.OptimisticLockException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.StaleObjectStateException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuctionScheduler {

    private final AuctionRepository auctionRepository;
    private final RedisFacade redisFacade;

    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void updateStatusScheduled() {

        LocalDateTime now = LocalDateTime.now();

        List<Auction> toStart = auctionRepository.findByStatusAndStartTimeBefore(
            AuctionStatus.PENDING, now);
        for (Auction auction : toStart) {
            try {
                auction.updateStatusActive();
                redisFacade.generateAuctionStatus(auction.getId(), auction.getEndTime());
                redisFacade.generateStartingBid(auction.getId(), auction.getStartingBid(), auction.getEndTime());
                redisFacade.maxBid(auction.getId(), 0L, auction.getStartingBid(), auction.getEndTime());
                log.info("PENDING -> ACTIVE 상태 변경 완료 (ID: {})", auction.getId());
            } catch (OptimisticLockException | StaleObjectStateException e) {
                log.error("PENDING -> ACTIVE 상태 변경 실패(낙관적 락) - id: {}, 에러: {}",
                    auction.getId(), e.getMessage());
                throw e;
            }
        }

        List<Auction> toEnd = auctionRepository.findByStatusAndEndTimeBefore(
            AuctionStatus.ACTIVE, now);
        for (Auction auction : toEnd) {
            try {
                auction.updateStatusClose();
                log.info("ACTIVE -> CLOSED 상태 변경 완료 (ID: {})", auction.getId());

                MaxBidData maxBidData = redisFacade.getMaxBidInfo(auction.getId());
                Long userId = maxBidData.userId();
                BigDecimal finalBid = maxBidData.bid();

                auction.assignWinner(userId, finalBid);
                log.info("최종 입찰자 설정 완료 (auctionId: {}, userId: {}, bid: {})",
                    auction.getId(), maxBidData.userId(), maxBidData.bid());

            } catch (OptimisticLockException | StaleObjectStateException e) {
                log.error("ACTIVE -> CLOSED 상태 변경 실패(낙관적 락) - id: {}, 에러: {}",
                    auction.getId(), e.getMessage());
                throw e;
            }
        }

    }
}
