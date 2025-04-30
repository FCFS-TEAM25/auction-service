package com.sparta.limited.auction_service.auction.infrastructure.redis;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisLuaScriptService {

    private final StringRedisTemplate redisTemplate;
    private final RedisScript<Long> bidLuaScript;

    public Long executeBidValidation(UUID auctionId, Long userId, BigDecimal bid) {
        List<String> redisKeys = generateBidKeys(auctionId);

        Long result = redisTemplate.execute(
            bidLuaScript,
            redisKeys,
            String.valueOf(userId),
            String.valueOf(bid),
            auctionId.toString()
        );

        return result;

    }

    private List<String> generateBidKeys(UUID auctionId) {
        return Arrays.asList(
            "bidStatus:" + auctionId,
            "bidStartingBid:" + auctionId,
            "maxBid:" + auctionId
        );
    }

}
