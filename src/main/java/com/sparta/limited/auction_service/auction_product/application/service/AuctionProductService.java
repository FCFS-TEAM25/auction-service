package com.sparta.limited.auction_service.auction_product.application.service;

import com.sparta.limited.auction_service.auction_product.application.dto.request.AuctionProductCreateRequest;
import com.sparta.limited.auction_service.auction_product.application.dto.response.AuctionProductCreateResponse;
import com.sparta.limited.auction_service.auction_product.application.dto.response.AuctionProductReadResponse;
import java.util.UUID;

public interface AuctionProductService {

    AuctionProductCreateResponse createAuctionProduct(AuctionProductCreateRequest request);

    AuctionProductReadResponse getAuctionProduct(UUID id);
}
