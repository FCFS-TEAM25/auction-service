package com.sparta.limited.auction_service.auction_product.application.mapper;

import com.sparta.limited.auction_service.auction_product.application.dto.response.AuctionProductCreateResponse;
import com.sparta.limited.auction_service.auction_product.application.dto.response.AuctionProductReadResponse;
import com.sparta.limited.auction_service.auction_product.domain.model.AuctionProduct;
import com.sparta.limited.auction_service.auction_product.application.service.product.ProductInfo;

public class AuctionProductMapper {

    public static AuctionProduct toEntity(ProductInfo productInfo) {
        return AuctionProduct.of(productInfo.productId(), productInfo.title(),
            productInfo.description(), productInfo.price());
    }

    public static AuctionProductCreateResponse toResponse(AuctionProduct auctionProduct) {
        return AuctionProductCreateResponse.of(auctionProduct.getId(),
            auctionProduct.getProductId(),
            auctionProduct.getTitle(), auctionProduct.getDescription(), auctionProduct.getPrice(),
            auctionProduct.getQuantity());
    }

    public static AuctionProductReadResponse toReadResponse(AuctionProduct auctionProduct) {
        return AuctionProductReadResponse.of(auctionProduct.getId(),
            auctionProduct.getProductId(),
            auctionProduct.getTitle(), auctionProduct.getDescription(), auctionProduct.getPrice(),
            auctionProduct.getQuantity());
    }

}
