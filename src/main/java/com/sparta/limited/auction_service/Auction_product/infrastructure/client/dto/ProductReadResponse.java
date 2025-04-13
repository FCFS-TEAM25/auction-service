package com.sparta.limited.auction_service.Auction_product.infrastructure.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;

@Getter
public class ProductReadResponse {

    @JsonProperty("id")
    private final UUID productId;
    private final String title;
    private final String description;
    private final BigDecimal price;

    public ProductReadResponse(UUID id, String title, String description,
        BigDecimal price) {
        this.productId = id;
        this.title = title;
        this.description = description;
        this.price = price;
    }

}
