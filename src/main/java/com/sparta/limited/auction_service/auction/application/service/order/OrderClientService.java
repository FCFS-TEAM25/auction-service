package com.sparta.limited.auction_service.auction.application.service.order;

import com.sparta.limited.auction_service.auction.application.dto.request.AuctionCreateOrderRequest;
import com.sparta.limited.auction_service.auction.infrastructure.client.OrderClient;
import com.sparta.limited.auction_service.auction.infrastructure.client.dto.OrderCreateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderClientService {

    private final OrderClient orderClient;

    public OrderInfo createOrder(Long userId, AuctionCreateOrderRequest request) {
        log.info("userId: "+userId);
        log.info("request: "+request);
        OrderCreateResponse response = orderClient.createOrder(userId, request);
        log.info("response: "+
                response.getId()+
            userId+
            response.getUsername()+
            response.getAddress()+
            response.getStatus()+
            response.getOrderType()+
            response.getProductId()+
            response.getQuantity()+
            response.getPrice());
        return OrderInfo.from(
            response.getId(),
            userId,
            response.getUsername(),
            response.getAddress(),
            response.getStatus(),
            response.getOrderType(),
            response.getProductId(),
            response.getQuantity(),
            response.getPrice());
    }

}
