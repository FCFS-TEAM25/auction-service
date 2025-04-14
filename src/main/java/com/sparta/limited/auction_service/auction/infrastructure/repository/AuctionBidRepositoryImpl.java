package com.sparta.limited.auction_service.auction.infrastructure.repository;

import com.sparta.limited.auction_service.auction.domain.exception.AuctionErrorCode;
import com.sparta.limited.auction_service.auction.domain.model.Auction;
import com.sparta.limited.auction_service.auction.domain.model.AuctionUser;
import com.sparta.limited.auction_service.auction.domain.repository.AuctionBidRepository;
import com.sparta.limited.auction_service.auction.domain.repository.AuctionRepository;
import com.sparta.limited.auction_service.auction.infrastructure.persistence.JpaAuctionBidRepository;
import com.sparta.limited.auction_service.auction.infrastructure.persistence.JpaAuctionRepository;
import java.util.Optional;
import java.util.UUID;
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

    @Override
    public boolean existsByAuctionIdAndUserId(UUID auctionId, Long userId) {
        return jpaAuctionBidRepository.existsByAuctionIdAndUserId(auctionId, userId);
    }

    @Override
    public AuctionUser findFirstByAuctionIdOrderByBidDescCreatedAtAsc(UUID auctionId) {
        return jpaAuctionBidRepository.findFirstByAuctionIdOrderByBidDescCreatedAtAsc(auctionId)
            .orElseThrow(() -> AuctionErrorCode.NO_BIDS_FOUND.toException());
    }

}
