package com.sparta.limited.auction_service.auction.presentation.controller;

import com.sparta.limited.auction_service.auction.application.dto.request.AuctionCreateBidRequest;
import com.sparta.limited.auction_service.auction.application.dto.request.AuctionCreateRequest;
import com.sparta.limited.auction_service.auction.application.dto.response.AuctionCreateBidResponse;
import com.sparta.limited.auction_service.auction.application.dto.response.AuctionCreateResponse;
import com.sparta.limited.auction_service.auction.application.service.AuctionService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auctions")
public class AuctionController {

    private final AuctionService auctionService;

    @PostMapping
    ResponseEntity<AuctionCreateResponse> createAuction
        (@RequestBody AuctionCreateRequest request) {
        AuctionCreateResponse response = auctionService.createAuction(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{auctionId}")
    ResponseEntity<AuctionCreateBidResponse> createAuctionBid(
        @PathVariable UUID auctionId,
        @RequestHeader("X-User-Id") Long userId,
        @RequestBody AuctionCreateBidRequest request) {
        AuctionCreateBidResponse response = auctionService.createAuctionBid(auctionId, userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
