package com.sparta.limited.auction_service.Auction_product.domain.repository;

import com.sparta.limited.auction_service.Auction_product.domain.model.AuctionProduct;

public interface AuctionProductRepository {

    void save(AuctionProduct auctionProduct);
}
