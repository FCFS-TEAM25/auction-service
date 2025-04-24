package com.sparta.limited.auction_service.auction_product.infrastructure.client;

import com.sparta.limited.auction_service.auction_product.infrastructure.client.dto.ProductReadResponse;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/api/v1/internal/products/{productId}")
    ProductReadResponse getProduct(@PathVariable UUID productId);
}
