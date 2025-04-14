package com.sparta.limited.auction_service.Auction_product.presentation.controller;

import com.sparta.limited.auction_service.Auction_product.application.dto.request.AuctionProductCreateRequest;
import com.sparta.limited.auction_service.Auction_product.application.dto.response.AuctionProductCreateResponse;
import com.sparta.limited.auction_service.Auction_product.application.service.AuctionProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auction-products")
@Slf4j
public class AuctionProductController {

    private final AuctionProductService auctionProductService;

    @PostMapping
    ResponseEntity<AuctionProductCreateResponse> createAuctionProduct(
        @RequestBody AuctionProductCreateRequest request) {
        log.info("controller: " + auctionProductService.createAuctionProduct(request));
        AuctionProductCreateResponse response = auctionProductService.createAuctionProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
