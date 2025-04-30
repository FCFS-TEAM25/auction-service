package com.sparta.limited.auction_service.auction.application.service;

import com.sparta.limited.auction_service.auction.application.dto.request.AuctionCreateBidRequest;
import com.sparta.limited.auction_service.auction.application.dto.request.AuctionCreateOrderRequest;
import com.sparta.limited.auction_service.auction.application.dto.request.AuctionCreateRequest;
import com.sparta.limited.auction_service.auction.application.dto.response.AuctionCreateBidResponse;
import com.sparta.limited.auction_service.auction.application.dto.response.AuctionCreateOrderResponse;
import com.sparta.limited.auction_service.auction.application.dto.response.AuctionCreateResponse;
import com.sparta.limited.auction_service.auction.application.dto.response.AuctionReadResponse;
import com.sparta.limited.auction_service.auction.application.mapper.AuctionBidMapper;
import com.sparta.limited.auction_service.auction.application.mapper.AuctionMapper;
import com.sparta.limited.auction_service.auction.application.service.order.OrderClientService;
import com.sparta.limited.auction_service.auction.application.service.order.OrderInfo;
import com.sparta.limited.auction_service.auction.domain.model.Auction;
import com.sparta.limited.auction_service.auction.domain.model.AuctionUser;
import com.sparta.limited.auction_service.auction.domain.repository.AuctionBidRepository;
import com.sparta.limited.auction_service.auction.domain.repository.AuctionRepository;
import com.sparta.limited.auction_service.auction.domain.validator.AuctionValidator;
import com.sparta.limited.auction_service.auction.domain.validator.LuaScriptResultHandler;
import com.sparta.limited.auction_service.auction.infrastructure.redis.RedisFacade;
import com.sparta.limited.auction_service.auction_product.domain.model.AuctionProduct;
import com.sparta.limited.auction_service.auction_product.domain.repository.AuctionProductRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {

    private final AuctionRepository auctionRepository;
    private final AuctionBidRepository auctionBidRepository;
    private final AuctionProductRepository auctionProductRepository;
    private final AuctionValidator auctionValidator;
    private final OrderClientService orderClientService;

    private final RedisFacade redisFacade;
    private final LuaScriptResultHandler luaScriptResultHandler;

    @Transactional
    public AuctionCreateResponse createAuction(AuctionCreateRequest request) {
        auctionProductRepository.findByProductId(request.getAuctionProductId());
        Auction auction = AuctionMapper.toEntity(request);
        auctionRepository.save(auction);
        return AuctionMapper.toResponse(auction);
    }

    @Transactional
    public AuctionCreateBidResponse createAuctionBid(UUID auctionId, Long userId, AuctionCreateBidRequest request) {
        Long result = redisFacade.executeBidValidation(auctionId, userId, request.getBid());
        luaScriptResultHandler.resultHandler(result, auctionId);

        AuctionUser bid = AuctionBidMapper.toEntity(auctionId, userId, request);
        auctionBidRepository.save(bid);
        return AuctionBidMapper.toResponse(bid);
    }

    @Transactional
    public AuctionCreateOrderResponse createOrder(UUID auctionId, Long userId,
        AuctionCreateOrderRequest request) {

        Auction auction = auctionRepository.findById(auctionId);

        auctionValidator.validateAuctionForOrder(auction);
        auctionValidator.validateWinner(auctionId, userId);

        OrderInfo orderInfo = orderClientService.createOrder(userId, request);

        AuctionProduct auctionProduct = auctionProductRepository.findByProductId(auction.getAuctionProductId());
        auctionProduct.decreaseQuantity();

        return AuctionMapper.toOrderResponse(orderInfo);

    }

    @Transactional(readOnly = true)
    public AuctionReadResponse getAuction(UUID auctionId) {
        Auction auction = auctionRepository.findById(auctionId);
        AuctionProduct auctionProduct = auctionProductRepository.findByProductId(auction.getAuctionProductId());
        return AuctionMapper.toReadResponse(auction, auctionProduct);
    }

}
