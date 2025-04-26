package com.sparta.limited.auction_service.auction.infrastructure.persistence;

import com.sparta.limited.auction_service.auction.domain.model.Auction;
import com.sparta.limited.auction_service.auction.domain.model.AuctionUser;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAuctionBidRepository extends JpaRepository<AuctionUser, UUID> {

    boolean existsByAuctionIdAndUserId(UUID auctionId, Long userId);

    Optional<AuctionUser> findFirstByAuctionIdOrderByBidDescCreatedAtAsc(UUID auctionId);
}
