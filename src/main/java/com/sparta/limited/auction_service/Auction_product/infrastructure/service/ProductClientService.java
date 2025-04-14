package com.sparta.limited.auction_service.Auction_product.infrastructure.service;

import com.sparta.limited.auction_service.Auction_product.infrastructure.client.ProductClient;
import com.sparta.limited.auction_service.Auction_product.infrastructure.client.dto.ProductInfo;
import com.sparta.limited.auction_service.Auction_product.infrastructure.client.dto.ProductReadResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductClientService {

    private final ProductClient productClient;

    public ProductInfo getProduct(UUID productId) {
        ProductReadResponse response = productClient.getProduct(productId);
        return ProductInfo.from(
            response.getProductId(),
            response.getTitle(),
            response.getDescription(),
            response.getPrice()
        );
    }

}
