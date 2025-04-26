package com.sparta.limited.auction_service.auction.application.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class AuctionCreateRequest {

    private UUID auctionProductId;
    private BigDecimal startingBid;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private AuctionCreateRequest(UUID auctionProductId, BigDecimal startingBid, LocalDateTime startTime, LocalDateTime endTime) {
        this.auctionProductId = auctionProductId;
        this.startingBid = startingBid;
        this.startTime = startTime;
        this.endTime = endTime;

    }

    public static AuctionCreateRequest of(UUID auctionProductId, BigDecimal startingBid, LocalDateTime startTime, LocalDateTime endTime) {
        return new AuctionCreateRequest(auctionProductId, startingBid, startTime, endTime);
    }

}
