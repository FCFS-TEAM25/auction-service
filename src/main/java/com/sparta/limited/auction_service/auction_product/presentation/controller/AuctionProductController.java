package com.sparta.limited.auction_service.auction_product.presentation.controller;

import com.sparta.limited.auction_service.auction_product.application.dto.request.AuctionProductCreateRequest;
import com.sparta.limited.auction_service.auction_product.application.dto.response.AuctionProductCreateResponse;
import com.sparta.limited.auction_service.auction_product.application.service.AuctionProductService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auction-products")
public class AuctionProductController {

    private final AuctionProductService auctionProductService;

    @PostMapping
    ResponseEntity<AuctionProductCreateResponse> createAuctionProduct(
        @RequestBody AuctionProductCreateRequest request) {
        AuctionProductCreateResponse response = auctionProductService.createAuctionProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    ResponseEntity<AuctionProductCreateResponse> getAuctionProduct(
        @PathVariable("id") UUID id) {
        AuctionProductCreateResponse response = auctionProductService.getAuctionProduct(id);
        return ResponseEntity.ok(response);
    }
}
