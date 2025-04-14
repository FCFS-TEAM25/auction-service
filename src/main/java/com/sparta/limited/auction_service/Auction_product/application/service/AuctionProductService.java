package com.sparta.limited.auction_service.Auction_product.application.service;

import com.sparta.limited.auction_service.Auction_product.application.dto.request.AuctionProductCreateRequest;
import com.sparta.limited.auction_service.Auction_product.application.dto.response.AuctionProductCreateResponse;

public interface AuctionProductService {

    AuctionProductCreateResponse createAuctionProduct(AuctionProductCreateRequest request);
}
