package com.sparta.limited.auction_service.auction.infrastructure.client;

import com.sparta.limited.auction_service.auction.application.dto.request.OrderCreateRequest;
import com.sparta.limited.auction_service.auction.infrastructure.client.dto.OrderCreateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "order-service", url = "http://localhost:19094/api/v1/internal/orders")
public interface OrderClient {

    @PostMapping
    OrderCreateResponse createOrder(
        @RequestHeader("X-User-Id") Long userId,
        @RequestBody OrderCreateRequest request);
}
