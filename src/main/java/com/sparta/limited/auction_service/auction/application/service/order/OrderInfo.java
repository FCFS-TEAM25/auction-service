package com.sparta.limited.auction_service.auction.application.service.order;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderInfo(
    UUID id,
    Long userId,
    String username,
    String address,
    String status,
    String orderType,
    UUID productId,
    int quantity,
    BigDecimal price

) {

    public static OrderInfo from(UUID id, Long userId, String username, String address, String status, String orderType, UUID productId, int quantity, BigDecimal price) {
        return new OrderInfo(id, userId, username, address, status, orderType, productId, quantity, price);
    }

}
