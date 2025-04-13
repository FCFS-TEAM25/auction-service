package com.sparta.limited.auction_service.auction.application.dto.reponse;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;

@Getter
public class AuctionUserCreateResponse {

    private UUID id;
    private UUID auctionId;
    private Long userId;
    private BigDecimal bid;
}
