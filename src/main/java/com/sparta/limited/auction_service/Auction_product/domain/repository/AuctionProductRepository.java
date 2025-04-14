package com.sparta.limited.auction_service.Auction_product.domain.repository;

import com.sparta.limited.auction_service.Auction_product.domain.model.AuctionProduct;
import com.sparta.limited.auction_service.auction.domain.model.Auction;
import java.util.UUID;

public interface AuctionProductRepository {

    void save(AuctionProduct auctionProduct);

    AuctionProduct findById(UUID id);

}
