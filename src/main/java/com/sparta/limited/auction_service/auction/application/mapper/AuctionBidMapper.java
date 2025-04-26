package com.sparta.limited.auction_service.auction.application.mapper;

import com.sparta.limited.auction_service.auction.application.dto.request.AuctionCreateBidRequest;
import com.sparta.limited.auction_service.auction.application.dto.request.AuctionCreateRequest;
import com.sparta.limited.auction_service.auction.application.dto.response.AuctionCreateBidResponse;
import com.sparta.limited.auction_service.auction.application.dto.response.AuctionCreateResponse;
import com.sparta.limited.auction_service.auction.domain.model.Auction;
import com.sparta.limited.auction_service.auction.domain.model.AuctionUser;
import java.util.UUID;

public class AuctionBidMapper {

    public static AuctionUser toEntity(UUID auctionId, Long userId, AuctionCreateBidRequest request) {
        return AuctionUser.of(auctionId, userId, request.getBid());
    }

    public static AuctionCreateBidResponse toResponse(AuctionUser auctionUser) {
        return AuctionCreateBidResponse.of(auctionUser.getId(), auctionUser.getAuctionId(),
            auctionUser.getUserId(), auctionUser.getBid());
    }

}
