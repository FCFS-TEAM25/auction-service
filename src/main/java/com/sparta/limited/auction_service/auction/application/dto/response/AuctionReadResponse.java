package com.sparta.limited.auction_service.auction.application.dto.response;

import com.sparta.limited.auction_service.auction.domain.model.AuctionStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class AuctionReadResponse {

    private UUID id;
    private Long userId;
    private UUID auctionProductId;
    private AuctionStatus status;
    private BigDecimal startingBid;
    private BigDecimal finalBid;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private AuctionReadResponse(UUID id, Long userId, UUID auctionProductId, AuctionStatus status,
        BigDecimal startingBid, BigDecimal finalBid, LocalDateTime startTime,
        LocalDateTime endTime) {
        this.id = id;
        this.userId = userId;
        this.auctionProductId = auctionProductId;
        this.status = status;
        this.startingBid = startingBid;
        this.finalBid = finalBid;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static AuctionReadResponse of(UUID id, Long userId, UUID auctionProductId,
        AuctionStatus status,
        BigDecimal startingBid, BigDecimal finalBid, LocalDateTime startTime,
        LocalDateTime endTime) {
        return new AuctionReadResponse(id, userId, auctionProductId, status, startingBid,
            finalBid, startTime, endTime);
    }

}
