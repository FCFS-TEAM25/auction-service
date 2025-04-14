package com.sparta.limited.auction_service.auction.infrastructure.repository;

import com.sparta.limited.auction_service.auction.domain.model.Auction;
import com.sparta.limited.auction_service.auction.domain.model.AuctionUser;
import com.sparta.limited.auction_service.auction.domain.repository.AuctionBidRepository;
import com.sparta.limited.auction_service.auction.domain.repository.AuctionRepository;
import com.sparta.limited.auction_service.auction.infrastructure.persistence.JpaAuctionBidRepository;
import com.sparta.limited.auction_service.auction.infrastructure.persistence.JpaAuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuctionBidRepositoryImpl implements AuctionBidRepository {

    private final JpaAuctionBidRepository jpaAuctionBidRepository;

    @Override
    public void save(AuctionUser auctionUser) {
        jpaAuctionBidRepository.save(auctionUser);
    }
}
