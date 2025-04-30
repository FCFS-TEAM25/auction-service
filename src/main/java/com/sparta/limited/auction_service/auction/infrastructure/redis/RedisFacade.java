package com.sparta.limited.auction_service.auction.infrastructure.redis;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public interface RedisFacade {

    // 경매 캐시 관련
    void generateAuctionStatus(UUID auctionId, LocalDateTime endTime);
    void generateStartingBid(UUID auctionId, BigDecimal bid, LocalDateTime endTime);
    void maxBid(UUID auctionId, Long userId, BigDecimal bid, LocalDateTime endTime);

    // 입찰 관련
    Long executeBidValidation(UUID auctionId, Long userId, BigDecimal bid);
}
