package com.sparta.limited.auction_service.Auction_product.application.service;

import com.sparta.limited.auction_service.Auction_product.application.dto.request.AuctionProductCreateRequest;
import com.sparta.limited.auction_service.Auction_product.application.dto.response.AuctionProductCreateResponse;
import com.sparta.limited.auction_service.Auction_product.application.mapper.AuctionProductMapper;
import com.sparta.limited.auction_service.Auction_product.domain.model.AuctionProduct;
import com.sparta.limited.auction_service.Auction_product.domain.repository.AuctionProductRepository;
import com.sparta.limited.auction_service.Auction_product.infrastructure.client.dto.ProductInfo;
import com.sparta.limited.auction_service.Auction_product.infrastructure.service.ProductService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuctionProductServiceImpl implements AuctionProductService {

    private final AuctionProductRepository auctionProductRepository;
    private final ProductService productService;

    @Transactional
    public AuctionProductCreateResponse createAuctionProduct(AuctionProductCreateRequest request) {
        log.info("productId: "+ request.getProductId());
        UUID productId = request.getProductId();

        ProductInfo productInfo = productService.getProduct(productId);

        log.info("productInfo: "+ productInfo);
        AuctionProduct auctionProduct = AuctionProductMapper.toEntity(productInfo);
        log.info("auctionProduct: "+ auctionProduct);
        auctionProductRepository.save(auctionProduct);
        return AuctionProductMapper.toResponse(auctionProduct);
    }
}
