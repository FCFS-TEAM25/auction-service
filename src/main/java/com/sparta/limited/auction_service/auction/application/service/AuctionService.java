package com.sparta.limited.auction_service.auction.application.service;

import com.sparta.limited.auction_service.auction.application.dto.request.AuctionCreateRequest;
import com.sparta.limited.auction_service.auction.application.dto.response.AuctionCreateResponse;

public interface AuctionService {

    AuctionCreateResponse createAuction(AuctionCreateRequest request);

}
