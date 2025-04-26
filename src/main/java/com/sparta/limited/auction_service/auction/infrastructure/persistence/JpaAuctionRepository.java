package com.sparta.limited.auction_service.auction.infrastructure.persistence;

import com.sparta.limited.auction_service.auction.domain.model.Auction;
import com.sparta.limited.auction_service.auction.domain.model.AuctionStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAuctionRepository extends JpaRepository<Auction, UUID> {

    Optional<Auction> findById(UUID auctionId);

    boolean existsByIdAndUserId(UUID auctionId, Long userId);

    List<Auction> findByStatusAndStartTimeBefore(AuctionStatus status, LocalDateTime startTime);

    List<Auction> findByStatusAndEndTimeBefore(AuctionStatus status, LocalDateTime endTime);
}
