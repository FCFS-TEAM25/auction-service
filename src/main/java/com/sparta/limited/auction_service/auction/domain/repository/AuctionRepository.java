package com.sparta.limited.auction_service.auction.domain.repository;

import com.sparta.limited.auction_service.auction.domain.model.Auction;
import java.util.UUID;

public interface AuctionRepository {

    void save(Auction auction);

    Auction findById(UUID auctionId);

    boolean existsByIdAndUserId(UUID auctionId, Long userId);
}
