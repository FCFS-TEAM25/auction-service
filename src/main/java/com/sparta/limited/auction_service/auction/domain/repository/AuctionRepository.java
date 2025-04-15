package com.sparta.limited.auction_service.auction.domain.repository;

import com.sparta.limited.auction_service.auction.domain.model.Auction;
import com.sparta.limited.auction_service.auction.domain.model.AuctionStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AuctionRepository {

    void save(Auction auction);

    Auction findById(UUID auctionId);

    boolean existsByIdAndUserId(UUID auctionId, Long userId);

    List<Auction> findByStatusAndStartTimeBefore(AuctionStatus status, LocalDateTime startTime);

    List<Auction> findByStatusAndEndTimeBefore(AuctionStatus status, LocalDateTime endTime);
}
