package com.sparta.limited.auction_service.Auction_product.application.dto.request;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;

@Getter
public class AuctionProductCreateRequest {

    private UUID productId;
    private String title;
    private String description;
    private BigDecimal price;
}
