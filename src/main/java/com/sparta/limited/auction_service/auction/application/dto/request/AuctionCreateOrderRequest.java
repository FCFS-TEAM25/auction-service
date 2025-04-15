package com.sparta.limited.auction_service.auction.application.dto.request;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;

@Getter
public class AuctionCreateOrderRequest {

    private String orderType;
    private UUID productId;
    private int quantity;
    private BigDecimal price;

    private AuctionCreateOrderRequest(String orderType, UUID productId, int quantity, BigDecimal price) {
        this.orderType = orderType;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public static AuctionCreateOrderRequest of(String orderType, UUID productId, int quantity, BigDecimal price) {
        return new AuctionCreateOrderRequest(orderType, productId, quantity, price);
    }

}
