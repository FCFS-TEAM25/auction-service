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

}
