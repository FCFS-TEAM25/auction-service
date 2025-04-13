package com.sparta.limited.auction_service.auction.application.dto.reponse;

import com.sparta.limited.auction_service.auction.domain.model.AuctionStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class AuctionCreateResponse {

    private UUID id;
    private Long userId;
    private UUID auctionProductId;
    private AuctionStatus status;
    private BigDecimal startingBid;
    private BigDecimal finalBid;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private AuctionCreateResponse(UUID id, Long userId, UUID auctionProductId, AuctionStatus status,
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

    public static AuctionCreateResponse of(UUID id, Long userId, UUID auctionProductId,
        AuctionStatus status,
        BigDecimal startingBid, BigDecimal finalBid, LocalDateTime startTime,
        LocalDateTime endTime) {
        return new AuctionCreateResponse(id, userId, auctionProductId, status, startingBid,
            finalBid, startTime, endTime);
    }

}
