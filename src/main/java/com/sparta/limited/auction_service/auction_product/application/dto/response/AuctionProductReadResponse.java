package com.sparta.limited.auction_service.auction_product.application.dto.response;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;

@Getter
public class AuctionProductReadResponse {

    private UUID id;
    private UUID productId;
    private String title;
    private String description;
    private BigDecimal price;
    private int quantity;

    private AuctionProductReadResponse(UUID id, UUID productId, String title, String description,
        BigDecimal price, int quantity) {
        this.id = id;
        this.productId = productId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public static AuctionProductReadResponse of(UUID id, UUID ProductId, String title,
        String description, BigDecimal price, int quantity) {
        return new AuctionProductReadResponse(id, ProductId, title, description, price, quantity);
    }
}
