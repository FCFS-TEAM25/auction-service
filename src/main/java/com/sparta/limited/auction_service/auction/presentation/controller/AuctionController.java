package com.sparta.limited.auction_service.auction.presentation.controller;

import com.sparta.limited.auction_service.auction.application.dto.request.AuctionCreateBidRequest;
import com.sparta.limited.auction_service.auction.application.dto.request.AuctionCreateOrderRequest;
import com.sparta.limited.auction_service.auction.application.dto.request.AuctionCreateRequest;
import com.sparta.limited.auction_service.auction.application.dto.response.AuctionCreateBidResponse;
import com.sparta.limited.auction_service.auction.application.dto.response.AuctionCreateResponse;
import com.sparta.limited.auction_service.auction.application.dto.response.AuctionCreateOrderResponse;
import com.sparta.limited.auction_service.auction.application.dto.response.AuctionReadResponse;
import com.sparta.limited.auction_service.auction.application.service.AuctionService;
import com.sparta.limited.common_module.common.annotation.CurrentUserId;
import com.sparta.limited.common_module.common.aop.RoleCheck;
import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auctions")
public class AuctionController {

    private final AuctionService auctionService;

    @RoleCheck("ROLE_ADMIN")
    @PostMapping
    ResponseEntity<AuctionCreateResponse> createAuction
        (@RequestBody AuctionCreateRequest request) {
        AuctionCreateResponse response = auctionService.createAuction(request);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/v1/auctions/{id}")
            .buildAndExpand(response.getId())
            .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @RoleCheck("ROLE_USER")
    @PostMapping("/{auctionId}")
    ResponseEntity<AuctionCreateBidResponse> createAuctionBid(
        @PathVariable UUID auctionId,
        @CurrentUserId Long userId,
        @RequestBody AuctionCreateBidRequest request) {
        AuctionCreateBidResponse response = auctionService.createAuctionBid(auctionId, userId, request);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/v1/auctions/{auctionId}")
            .buildAndExpand(auctionId)
            .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @RoleCheck("ROLE_USER")
    @PostMapping("/{auctionId}/purchase")
    ResponseEntity<AuctionCreateOrderResponse> createOrder (
        @PathVariable UUID auctionId,
        @CurrentUserId Long userId,
        @RequestBody AuctionCreateOrderRequest request) {
        AuctionCreateOrderResponse response = auctionService.createOrder(auctionId, userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{auctionId}")
    ResponseEntity<AuctionReadResponse> getAuction(
        @PathVariable UUID auctionId) {
        AuctionReadResponse response = auctionService.getAuction(auctionId);
        return ResponseEntity.ok(response);
    }
}
