package com.sparta.limited.auction_service.auction.application.dto.response;

import com.sparta.limited.auction_service.auction.domain.model.AuctionStatus;
import jakarta.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class AuctionCreateBidResponse {

    private final UUID id;
    private final UUID auctionId;
    private final Long userId;
    private final BigDecimal bid;

    private AuctionCreateBidResponse(UUID id, UUID auctionId, Long userId, BigDecimal bid) {
        this.id = id;
        this.auctionId = auctionId;
        this.userId = userId;
        this.bid = bid;
    }

    public static AuctionCreateBidResponse of(UUID id, UUID auctionId, Long userId, BigDecimal bid) {
        return new AuctionCreateBidResponse(id, auctionId, userId, bid);
    }

}
