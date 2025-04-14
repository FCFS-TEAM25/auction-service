package com.sparta.limited.auction_service.auction_product.application.dto.request;

import java.util.UUID;
import lombok.Getter;

@Getter
public class AuctionProductCreateRequest {

    private UUID productId;
}
