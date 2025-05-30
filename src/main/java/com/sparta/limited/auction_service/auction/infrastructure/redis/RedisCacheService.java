package com.sparta.limited.auction_service.auction.infrastructure.redis;

import com.sparta.limited.auction_service.auction.application.service.dto.MaxBidData;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisCacheService {

    private final StringRedisTemplate redisTemplate;

    public void generateAuctionStatus(UUID auctionId, LocalDateTime endTime) {
        String bidStatusKey = "bidStatus:" + auctionId;
        redisTemplate.opsForValue()
            .setIfAbsent(bidStatusKey, "1", Duration.ofSeconds(setTtlSeconds(endTime)));
    }

    public void generateStartingBid(UUID auctionId, BigDecimal startingBid, LocalDateTime endTime) {
        String bidStartBidKey = "bidStartingBid:" + auctionId;
        redisTemplate.opsForValue()
            .setIfAbsent(bidStartBidKey, String.valueOf(startingBid), Duration.ofSeconds(setTtlSeconds(endTime)));
    }

    public void maxBid(UUID auctionId, Long userId, BigDecimal bid, LocalDateTime endTime) {
        String maxBidKey = "maxBid:" + auctionId;
        BigDecimal startingBid = bid.subtract(BigDecimal.ONE);

        Map<String, String> bidInfo = new HashMap<>();
        bidInfo.put("userId", "0");
        bidInfo.put("bid", startingBid.toString());

        redisTemplate.opsForHash().putAll(maxBidKey, bidInfo);
        redisTemplate.expire(maxBidKey, setTtlSeconds(endTime), TimeUnit.SECONDS);
    }

    public MaxBidData getMaxBidInfo(UUID auctionId) {
        String maxBidKey = "maxBid:" + auctionId;
        Map<Object, Object> data = redisTemplate.opsForHash().entries(maxBidKey);

        String userIdStr = data.get("userId").toString();
        String bidStr = data.get("bid").toString();

        Long userId = Long.parseLong(userIdStr);
        BigDecimal bid = new BigDecimal(bidStr);

        MaxBidData maxBidData = new MaxBidData(auctionId, userId, bid);

        return maxBidData;
    }

    private Long setTtlSeconds(LocalDateTime date) {
        return Math.max(0, Duration.between(LocalDateTime.now(), date).toSeconds());
    }

}
