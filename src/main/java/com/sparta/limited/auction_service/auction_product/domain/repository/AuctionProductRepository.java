package com.sparta.limited.auction_service.auction_product.domain.repository;

import com.sparta.limited.auction_service.auction_product.domain.model.AuctionProduct;
import java.util.UUID;

public interface AuctionProductRepository {

    void save(AuctionProduct auctionProduct);

    AuctionProduct findById(UUID id);

}
