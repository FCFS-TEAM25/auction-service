package com.sparta.limited.auction_service.auction_product.infrastructure.client;

import com.sparta.limited.auction_service.auction_product.infrastructure.client.dto.ProductReadResponse;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "http://localhost:19093/api/v1/internal/products")
public interface ProductClient {

    @GetMapping("/{productId}")
    ProductReadResponse getProduct(@PathVariable UUID productId);
}
