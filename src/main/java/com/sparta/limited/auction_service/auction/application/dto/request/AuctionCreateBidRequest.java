package com.sparta.limited.auction_service.auction.application.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class AuctionCreateBidRequest {

    private BigDecimal bid;

}
