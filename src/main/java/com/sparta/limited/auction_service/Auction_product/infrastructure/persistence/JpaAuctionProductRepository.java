package com.sparta.limited.auction_service.Auction_product.infrastructure.persistence;

import com.sparta.limited.auction_service.Auction_product.domain.model.AuctionProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAuctionProductRepository extends JpaRepository<AuctionProduct, Long> {

}
