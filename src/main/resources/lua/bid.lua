local bidStatusKey = KEYS[1]
local bidStartBidKey = KEYS[2]
local maxBidKey = KEYS[3]

local userId = ARGV[1]
local bid = tonumber(ARGV[2])
local auctionId = ARGV[3]

local bidStatus = redis.call('EXISTS', bidStatusKey)
local startingBid = tonumber(redis.call('GET', bidStartBidKey))
local currentMaxBid = tonumber(redis.call("HGET", maxBidKey, "bid"))

if bidStatus == 0 then
    return 1;
end

if bid < startingBid then
    return 2;
end

local bidInfo = {
    auctionId = auctionId,
    userId = userId,
    bidPrice = bid,
    retryCount = 0
}

local encodedBidInfo = cjson.encode(bidInfo)
redis.call('RPUSH', 'bid_log_list', encodedBidInfo)

if not currentMaxBid or bid > currentMaxBid then
    redis.call("HMSET", maxBidKey, "userId", userId, "bid", bid)
end

return 0;
