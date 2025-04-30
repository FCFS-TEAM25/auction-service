package com.sparta.limited.auction_service.auction.infrastructure.redis;

import com.sparta.limited.auction_service.auction.application.service.dto.MaxBidData;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisFacadeImpl implements RedisFacade {
    private final RedisCacheService cacheService;
    private final RedisLuaScriptService luaScriptService;

    @Override
    public void generateAuctionStatus(UUID auctionId, LocalDateTime endTime) {
        cacheService.generateAuctionStatus(auctionId, endTime);
    }

    @Override
    public void generateStartingBid(UUID auctionId, BigDecimal bid, LocalDateTime endTime) {
        cacheService.generateStartingBid(auctionId, bid, endTime);
    }

    @Override
    public void maxBid(UUID auctionId, Long userId, BigDecimal bid, LocalDateTime endTime) {
        cacheService.maxBid(auctionId, userId, bid, endTime);
    }

    @Override
    public Long executeBidValidation(UUID auctionId, Long userId, BigDecimal bid) {
        return luaScriptService.executeBidValidation(auctionId, userId, bid);
    }

    @Override
    public MaxBidData getMaxBidInfo(UUID auctionId) {
        return cacheService.getMaxBidInfo(auctionId);
    }

}
