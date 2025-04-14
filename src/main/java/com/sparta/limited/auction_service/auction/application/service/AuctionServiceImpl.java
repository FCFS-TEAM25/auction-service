package com.sparta.limited.auction_service.auction.application.service;

import com.sparta.limited.auction_service.auction.application.dto.request.AuctionCreateBidRequest;
import com.sparta.limited.auction_service.auction.application.dto.request.AuctionCreateRequest;
import com.sparta.limited.auction_service.auction.application.dto.response.AuctionCreateBidResponse;
import com.sparta.limited.auction_service.auction.application.dto.response.AuctionCreateResponse;
import com.sparta.limited.auction_service.auction.application.mapper.AuctionBidMapper;
import com.sparta.limited.auction_service.auction.application.mapper.AuctionMapper;
import com.sparta.limited.auction_service.auction.domain.model.Auction;
import com.sparta.limited.auction_service.auction.domain.model.AuctionUser;
import com.sparta.limited.auction_service.auction.domain.repository.AuctionBidRepository;
import com.sparta.limited.auction_service.auction.domain.repository.AuctionRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuctionServiceImpl implements AuctionService {

    private final AuctionRepository auctionRepository;
    private final AuctionBidRepository auctionBidRepository;

    @Transactional
    public AuctionCreateResponse createAuction(AuctionCreateRequest request) {
        Auction auction = AuctionMapper.toEntity(request);
        auctionRepository.save(auction);
        return AuctionMapper.toResponse(auction);
    }

    @Transactional
    public AuctionCreateBidResponse createAuctionBid(UUID auctionId, Long userId, AuctionCreateBidRequest request) {
        log.info("auctionId: "+ auctionId);
        AuctionUser bid = AuctionBidMapper.toEntity(auctionId, userId, request);
        log.info("getAuctionId: "+ bid.getAuctionId()+"getBid: "+bid.getBid()+ "getUserId: "+bid.getUserId()+ "getId: "+bid.getId());
        log.info("toResponse: "+ AuctionBidMapper.toResponse(bid).getAuctionId()
        +AuctionBidMapper.toResponse(bid).getBid()
        +AuctionBidMapper.toResponse(bid).getUserId()
        +AuctionBidMapper.toResponse(bid).getId()
        );
        auctionBidRepository.save(bid);
        return AuctionBidMapper.toResponse(bid);
    }

}
