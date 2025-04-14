package com.sparta.limited.auction_service.auction.application.service;

import com.sparta.limited.auction_service.auction.application.dto.request.AuctionCreateBidRequest;
import com.sparta.limited.auction_service.auction.application.dto.request.AuctionCreateRequest;
import com.sparta.limited.auction_service.auction.application.dto.response.AuctionCreateBidResponse;
import com.sparta.limited.auction_service.auction.application.dto.response.AuctionCreateResponse;
import java.util.UUID;

public interface AuctionService {

    AuctionCreateResponse createAuction(AuctionCreateRequest request);

    AuctionCreateBidResponse createAuctionBid(UUID auctionId, Long userId, AuctionCreateBidRequest request);

}
