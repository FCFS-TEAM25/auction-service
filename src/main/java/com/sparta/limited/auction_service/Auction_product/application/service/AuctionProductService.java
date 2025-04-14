package com.sparta.limited.auction_service.Auction_product.application.service;

import com.sparta.limited.auction_service.Auction_product.application.dto.request.AuctionProductCreateRequest;
import com.sparta.limited.auction_service.Auction_product.application.dto.response.AuctionProductCreateResponse;
import java.util.UUID;

public interface AuctionProductService {

    AuctionProductCreateResponse createAuctionProduct(AuctionProductCreateRequest request);

    AuctionProductCreateResponse getAuctionProduct(UUID id);
}
