package com.sparta.limited.auction_service.auction.application.dto.request;

import com.sparta.limited.auction_service.auction.application.dto.response.AuctionCreateBidResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class AuctionCreateBidRequest {

    private BigDecimal bid;

    private AuctionCreateBidRequest(BigDecimal bid) {
        this.bid = bid;
    }

    public static AuctionCreateBidRequest of(BigDecimal bid) {
        return new AuctionCreateBidRequest(bid);
    }

}
