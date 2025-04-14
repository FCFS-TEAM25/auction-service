package com.sparta.limited.auction_service.auction.domain.repository;

import com.sparta.limited.auction_service.auction.domain.model.AuctionUser;
import java.util.Optional;
import java.util.UUID;

public interface AuctionBidRepository {

    void save(AuctionUser auctionUser);

    boolean existsByAuctionIdAndUserId(UUID auctionId, Long userId);

    Optional<AuctionUser> findFirstByAuctionIdOrderByBidDescCreatedAtAsc(UUID auctionId);
}
