package com.sparta.limited.auction_service.Auction_product.infrastructure.repository;

import com.sparta.limited.auction_service.Auction_product.domain.model.AuctionProduct;
import com.sparta.limited.auction_service.Auction_product.domain.repository.AuctionProductRepository;
import com.sparta.limited.auction_service.Auction_product.infrastructure.persistence.JpaAuctionProductRepository;
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
}
