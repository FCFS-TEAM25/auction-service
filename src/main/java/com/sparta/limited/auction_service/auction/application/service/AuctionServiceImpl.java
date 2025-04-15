package com.sparta.limited.auction_service.auction.application.service;

import com.sparta.limited.auction_service.auction.application.dto.request.AuctionCreateBidRequest;
import com.sparta.limited.auction_service.auction.application.dto.request.AuctionCreateOrderRequest;
import com.sparta.limited.auction_service.auction.application.dto.request.AuctionCreateRequest;
import com.sparta.limited.auction_service.auction.application.dto.response.AuctionCreateBidResponse;
import com.sparta.limited.auction_service.auction.application.dto.response.AuctionCreateOrderResponse;
import com.sparta.limited.auction_service.auction.application.dto.response.AuctionCreateResponse;
import com.sparta.limited.auction_service.auction.application.dto.response.AuctionCreateWinnerResponse;
import com.sparta.limited.auction_service.auction.application.mapper.AuctionBidMapper;
import com.sparta.limited.auction_service.auction.application.mapper.AuctionMapper;
import com.sparta.limited.auction_service.auction.application.service.order.OrderClientService;
import com.sparta.limited.auction_service.auction.application.service.order.OrderInfo;
import com.sparta.limited.auction_service.auction.domain.model.Auction;
import com.sparta.limited.auction_service.auction.domain.model.AuctionUser;
import com.sparta.limited.auction_service.auction.domain.repository.AuctionBidRepository;
import com.sparta.limited.auction_service.auction.domain.repository.AuctionRepository;
import com.sparta.limited.auction_service.auction.domain.validator.AuctionValidator;
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

    @Transactional
    public AuctionCreateResponse createAuction(AuctionCreateRequest request) {
        Auction auction = AuctionMapper.toEntity(request);
        auctionRepository.save(auction);
        return AuctionMapper.toResponse(auction);
    }

    @Transactional
    public AuctionCreateBidResponse createAuctionBid(UUID auctionId, Long userId, AuctionCreateBidRequest request) {
        Auction auction = auctionRepository.findById(auctionId);

        auctionValidator.validateAuction(auction);
        auctionValidator.validateBidPrice(auction, request.getBid());
        auctionValidator.validateNoDuplicateBid(auctionId, userId);

        AuctionUser bid = AuctionBidMapper.toEntity(auctionId, userId, request);
        auctionBidRepository.save(bid);
        return AuctionBidMapper.toResponse(bid);
    }

    @Transactional
    public AuctionCreateWinnerResponse selectAuctionWinner(UUID auctionId) {
        Auction auction = auctionRepository.findById(auctionId);

        AuctionUser winner = auctionBidRepository.findFirstByAuctionIdOrderByBidDescCreatedAtAsc(auctionId);

        auction.assignWinner(winner.getUserId(), winner.getBid());

        return AuctionMapper.toWinnerResponse(auction);
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

}
