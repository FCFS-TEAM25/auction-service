package com.sparta.limited.auction_service.auction.infrastructure.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;

@Getter
public class OrderCreateResponse {

    @JsonProperty("id")
    private UUID id;
    private Long userId;
    private String username;
    private String address;
    private String orderType;
    private String status;
    private UUID productId;
    private int quantity;
    private BigDecimal price;

    public OrderCreateResponse(UUID id, Long userId, String username, String address, String orderType, String status, UUID productId, int quantity, BigDecimal price) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.address = address;
        this.orderType = orderType;
        this.status = status;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

}
