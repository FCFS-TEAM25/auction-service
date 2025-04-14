package com.sparta.limited.auction_service.auction.domain.repository;

import com.sparta.limited.auction_service.auction.domain.model.Auction;

public interface AuctionRepository {

    void save(Auction auction);
}
