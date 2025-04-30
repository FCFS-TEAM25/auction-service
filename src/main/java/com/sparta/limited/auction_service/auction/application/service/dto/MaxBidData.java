package com.sparta.limited.auction_service.auction.application.service.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record MaxBidData (
    UUID auctionId,
    Long userId,
    BigDecimal bid
){

}
