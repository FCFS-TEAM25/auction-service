package com.sparta.limited.auction_service.auction_product.infrastructure.repository;

import com.sparta.limited.auction_service.auction_product.domain.model.AuctionProduct;
import com.sparta.limited.auction_service.auction_product.domain.repository.AuctionProductRepository;
import com.sparta.limited.auction_service.auction_product.infrastructure.persistence.JpaAuctionProductRepository;
import com.sparta.limited.common_module.exception.BusinessException;
import com.sparta.limited.common_module.exception.ErrorCode;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuctionProductRepositoryImpl implements AuctionProductRepository {

    private final JpaAuctionProductRepository jpaAuctionProductRepository;

    @Override
    public void save(AuctionProduct auctionProduct) {
        jpaAuctionProductRepository.save(auctionProduct);
    }

    @Override
    public AuctionProduct findById(UUID id) {
        return jpaAuctionProductRepository.findById(id)
            .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCES_NOT_FOUND));
    }
}
