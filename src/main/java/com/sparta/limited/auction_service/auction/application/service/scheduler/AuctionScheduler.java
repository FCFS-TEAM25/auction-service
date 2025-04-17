package com.sparta.limited.auction_service.auction.application.service.scheduler;

import com.sparta.limited.auction_service.auction.domain.model.Auction;
import com.sparta.limited.auction_service.auction.domain.model.AuctionStatus;
import com.sparta.limited.auction_service.auction.domain.repository.AuctionRepository;
import jakarta.persistence.OptimisticLockException;
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

    //@Scheduled(cron = "0 * * * * *")
    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void updateStatusScheduled() {

        LocalDateTime now = LocalDateTime.now();

        List<Auction> toStart = auctionRepository.findByStatusAndStartTimeBefore(
            AuctionStatus.PENDING, now);
        for (Auction auction : toStart) {
            try {
                auction.updateStatusActive();
                log.info("PENDING -> ACTIVE 상태 변경 완료 (ID: {})", auction.getId());
            } catch (OptimisticLockException | StaleObjectStateException e) {
                log.error("PENDING -> ACTIVE 상태 변경 실패(낙관적 락) - id: {}, 에러: {}",
                    auction.getId(), e.getMessage());
                throw e; // 예외를 다시 던져 트랜잭션 롤백을 보장
            }
        }

        List<Auction> toEnd = auctionRepository.findByStatusAndEndTimeBefore(
            AuctionStatus.ACTIVE, now);
        for (Auction auction : toEnd) {
            try {
                auction.updateStatusClose();
                log.info("ACTIVE -> CLOSED 상태 변경 완료 (ID: {})", auction.getId());
            } catch (OptimisticLockException | StaleObjectStateException e) {
                log.error("ACTIVE -> CLOSED 상태 변경 실패(낙관적 락) - id: {}, 에러: {}",
                    auction.getId(), e.getMessage());
                throw e; // 예외를 다시 던져 트랜잭션 롤백을 보장
            }
        }

    }
}
