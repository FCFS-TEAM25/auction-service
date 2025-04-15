package com.sparta.limited.auction_service.auction.application.service.scheduler;

import com.sparta.limited.auction_service.auction.domain.model.Auction;
import com.sparta.limited.auction_service.auction.domain.model.AuctionStatus;
import com.sparta.limited.auction_service.auction.domain.repository.AuctionRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuctionScheduler {

    private final AuctionRepository auctionRepository;

    //@Scheduled(cron = "0 * * * * *")
    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void updateStatusScheduled() {

        LocalDateTime now = LocalDateTime.now();

        List<Auction> toStart = auctionRepository.findByStatusAndStartTimeBefore(
            AuctionStatus.PENDING, now);
        toStart.forEach(Auction::updateStatusActive);

        List<Auction> toEnd = auctionRepository.findByStatusAndEndTimeBefore(
            AuctionStatus.ACTIVE, now);
        toEnd.forEach(Auction::updateStatusClose);
    }
}
