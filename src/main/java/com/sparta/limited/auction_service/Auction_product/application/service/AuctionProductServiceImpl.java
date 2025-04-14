package com.sparta.limited.auction_service.Auction_product.application.service;

import com.sparta.limited.auction_service.Auction_product.application.dto.request.AuctionProductCreateRequest;
import com.sparta.limited.auction_service.Auction_product.application.dto.response.AuctionProductCreateResponse;
import com.sparta.limited.auction_service.Auction_product.application.mapper.AuctionProductMapper;
import com.sparta.limited.auction_service.Auction_product.domain.model.AuctionProduct;
import com.sparta.limited.auction_service.Auction_product.domain.repository.AuctionProductRepository;
import com.sparta.limited.auction_service.Auction_product.infrastructure.client.dto.ProductInfo;
import com.sparta.limited.auction_service.Auction_product.infrastructure.service.ProductClientService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuctionProductServiceImpl implements AuctionProductService {

    private final AuctionProductRepository auctionProductRepository;
    private final ProductClientService productClientService;

    @Transactional
    public AuctionProductCreateResponse createAuctionProduct(AuctionProductCreateRequest request) {
        UUID productId = request.getProductId();

        ProductInfo productInfo = productClientService.getProduct(productId);

        AuctionProduct auctionProduct = AuctionProductMapper.toEntity(productInfo);
        auctionProductRepository.save(auctionProduct);
        return AuctionProductMapper.toResponse(auctionProduct);
    }
}
