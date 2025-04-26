package com.sparta.limited.auction_service.auction_product.presentation.controller;

import com.sparta.limited.auction_service.auction_product.application.dto.request.AuctionProductCreateRequest;
import com.sparta.limited.auction_service.auction_product.application.dto.response.AuctionProductCreateResponse;
import com.sparta.limited.auction_service.auction_product.application.dto.response.AuctionProductReadResponse;
import com.sparta.limited.auction_service.auction_product.application.service.AuctionProductService;
import com.sparta.limited.common_module.common.aop.RoleCheck;
import java.net.URI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auction-products")
public class AuctionProductController {

    private final AuctionProductService auctionProductService;

    @RoleCheck("ROLE_ADMIN")
    @PostMapping
    ResponseEntity<AuctionProductCreateResponse> createAuctionProduct(
        @RequestBody AuctionProductCreateRequest request) {
        AuctionProductCreateResponse response = auctionProductService.createAuctionProduct(request);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/v1/auction-products/{id}")
            .buildAndExpand(response.getId())
            .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{id}")
    ResponseEntity<AuctionProductReadResponse> getAuctionProduct(
        @PathVariable("id") UUID id) {
        AuctionProductReadResponse response = auctionProductService.getAuctionProduct(id);
        return ResponseEntity.ok(response);
    }
}
