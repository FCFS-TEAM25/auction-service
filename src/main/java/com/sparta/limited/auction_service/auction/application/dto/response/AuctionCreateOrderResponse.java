package com.sparta.limited.auction_service.auction.application.dto.response;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;

@Getter
public class AuctionCreateOrderResponse {

    private UUID id;
    private Long userId;
    private String username;
    private String address;
    private String orderType;
    private String status;
    private UUID productId;
    private int quantity;
    private BigDecimal price;

    private AuctionCreateOrderResponse(UUID id, Long userId, String username, String address,
        String orderType, String status,
        UUID productId, int quantity, BigDecimal price) {
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

    public static AuctionCreateOrderResponse of(UUID id, Long userId, String username, String address,
        String orderType,
        String status,
        UUID productId, int quantity, BigDecimal price) {
        return new AuctionCreateOrderResponse(id, userId, username, address, orderType, status, productId,
            quantity, price);
    }

}
