package com.sparta.limited.auction_service.Auction_product.infrastructure.persistence;

import com.sparta.limited.auction_service.Auction_product.domain.model.AuctionProduct;
import com.sparta.limited.common_module.exception.BusinessException;
import com.sparta.limited.common_module.exception.ErrorCode;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAuctionProductRepository extends JpaRepository<AuctionProduct, Long> {
    Optional<AuctionProduct> findById(UUID id);
}
