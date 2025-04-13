package com.sparta.limited.auction_service.auction.infrastructure.persistence;

import com.sparta.limited.auction_service.auction.domain.model.Auction;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAuctionRepository extends JpaRepository<Auction, UUID> {

}
