package com.sparta.limited.auction_service.auction.domain.repository;

import com.sparta.limited.auction_service.auction.domain.model.AuctionUser;

public interface AuctionBidRepository {

    void save(AuctionUser auctionUser);
}
