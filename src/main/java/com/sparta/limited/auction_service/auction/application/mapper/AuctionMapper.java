package com.sparta.limited.auction_service.auction.application.mapper;

import com.sparta.limited.auction_service.auction.application.dto.request.AuctionCreateRequest;
import com.sparta.limited.auction_service.auction.application.dto.response.AuctionCreateOrderResponse;
import com.sparta.limited.auction_service.auction.application.dto.response.AuctionCreateResponse;
import com.sparta.limited.auction_service.auction.application.dto.response.AuctionCreateWinnerResponse;
import com.sparta.limited.auction_service.auction.application.service.order.OrderInfo;
import com.sparta.limited.auction_service.auction.domain.model.Auction;

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

    public static AuctionCreateWinnerResponse toWinnerResponse(Auction auction) {
        return AuctionCreateWinnerResponse.of(auction.getId(), auction.getUserId(),
            auction.getAuctionProductId(), auction.getStatus(),
            auction.getStartingBid(), auction.getFinalBid(), auction.getStartTime(),
            auction.getEndTime());
    }

    public static AuctionCreateOrderResponse toOrderResponse(OrderInfo orderInfo) {
        return AuctionCreateOrderResponse.of(orderInfo.id(), orderInfo.userId(),
            orderInfo.username(), orderInfo.address(),
            orderInfo.orderType(), orderInfo.status(),
            orderInfo.productId(),orderInfo.quantity(),orderInfo.price());
    }


}
