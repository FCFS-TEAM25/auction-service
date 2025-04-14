package com.sparta.limited.auction_service.auction.application.mapper;

import com.sparta.limited.auction_service.auction.application.dto.request.AuctionCreateRequest;
import com.sparta.limited.auction_service.auction.application.dto.response.AuctionCreateResponse;
import com.sparta.limited.auction_service.auction.application.dto.response.AuctionWinnerResponse;
import com.sparta.limited.auction_service.auction.domain.model.Auction;
import com.sparta.limited.auction_service.auction.domain.model.AuctionUser;

public class AuctionMapper {

    public static Auction toEntity(AuctionCreateRequest request) {
        return Auction.of(request.getAuctionProductId(),
            request.getStartingBid(),
            request.getStartTime(), request.getEndTime());
    }

    public static AuctionCreateResponse toResponse(Auction auction) {
        return AuctionCreateResponse.of(auction.getId(), auction.getUserId(),
            auction.getAuctionProductId(), auction.getStatus(),
            auction.getStartingBid(), auction.getFinalBid(), auction.getStartTime(),
            auction.getEndTime());
    }

    public static AuctionWinnerResponse toWinnerResponse(Auction auction) {
        return AuctionWinnerResponse.of(auction.getId(), auction.getUserId(),
            auction.getAuctionProductId(), auction.getStatus(),
            auction.getStartingBid(), auction.getFinalBid(), auction.getStartTime(),
            auction.getEndTime());
    }

}
