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
        toStart.forEach(auction -> {
            try {
                auction.updateStatusActive();
            } catch (OptimisticLockException | StaleObjectStateException e) {
                try {
                    Auction refreshedAuction = auctionRepository.findById(auction.getId());
                    refreshedAuction.updateStatusActive();
                } catch (Exception retryException) {
                    log.error("PENDING -> ACTIVE 상태 변경 재시도 실패(낙관적 락) - id: {}, 에러: {}",
                        auction.getId(), retryException.getMessage());
                }
            }
        });

        List<Auction> toEnd = auctionRepository.findByStatusAndEndTimeBefore(
            AuctionStatus.ACTIVE, now);
        toEnd.forEach(auction -> {
            try {
                auction.updateStatusClose();
            } catch (OptimisticLockException | StaleObjectStateException e) {
                try {
                    Auction refreshedAuction = auctionRepository.findById(auction.getId());
                    refreshedAuction.updateStatusClose();
                } catch (Exception retryException) {
                    log.error("ACTIVE -> CLOSED 상태 변경 재시도 실패(낙관적 락) - id: {}, 에러: {}",
                        auction.getId(), retryException.getMessage());
                }
            }
        });
    }
}
