package com.sparta.limited.auction_service.auction_product.infrastructure.persistence;

import com.sparta.limited.auction_service.auction_product.domain.model.AuctionProduct;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAuctionProductRepository extends JpaRepository<AuctionProduct, Long> {
    Optional<AuctionProduct> findById(UUID id);
}
