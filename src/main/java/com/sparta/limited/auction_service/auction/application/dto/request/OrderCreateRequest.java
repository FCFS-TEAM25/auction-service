package com.sparta.limited.auction_service.auction.application.dto.request;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;

@Getter
public class OrderCreateRequest {

    private String orderType;
    private UUID productId;
    private int quantity;
    private BigDecimal price;

    private OrderCreateRequest(String orderType, UUID productId, int quantity, BigDecimal price) {
        this.orderType = orderType;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public static OrderCreateRequest of(String orderType, UUID productId, int quantity, BigDecimal price) {
        return new OrderCreateRequest(orderType, productId, quantity, price);
    }

}
