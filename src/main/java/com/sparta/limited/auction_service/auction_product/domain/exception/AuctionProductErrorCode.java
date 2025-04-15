package com.sparta.limited.auction_service.auction_product.domain.exception;

import com.sparta.limited.common_module.exception.BusinessException;
import com.sparta.limited.common_module.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum AuctionProductErrorCode {
    AUCTION_PRODUCT_QUANTITY_ZERO(ErrorCode.OPERATION_NOT_ALLOWED, "이미 재고가 0개인 상품입니다.");

    private final ErrorCode errorCode;
    private final String detailMessage;

    AuctionProductErrorCode(ErrorCode errorCode, String detailMessage) {
        this.errorCode = errorCode;
        this.detailMessage = detailMessage;
    }

    public BusinessException toException() {
        return new BusinessException(errorCode, detailMessage);
    }

}
