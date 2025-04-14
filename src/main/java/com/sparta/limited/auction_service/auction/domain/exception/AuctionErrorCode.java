package com.sparta.limited.auction_service.auction.domain.exception;

import com.sparta.limited.common_module.exception.BusinessException;
import com.sparta.limited.common_module.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum AuctionErrorCode {
    // 경매 정보 관련
    AUCTION_NOT_FOUND(ErrorCode.RESOURCES_NOT_FOUND, "존재하지 않는 경매입니다."),
    AUCTION_CLOSED(ErrorCode.OPERATION_NOT_ALLOWED, "이미 마감된 경매입니다."),
    AUCTION_NOT_STARTED(ErrorCode.OPERATION_NOT_ALLOWED, "아직 시작되지 않은 경매입니다."),
    AUCTION_NOT_ACTIVE(ErrorCode.OPERATION_NOT_ALLOWED, "현재 진행 중인 경매가 아닙니다."),

    // 입찰 관련
    BID_BELOW_STARTING_PRICE(ErrorCode.INVALID_PARAMETER, "입찰가가 시작가보다 낮습니다."),
    DUPLICATE_BID(ErrorCode.DUPLICATE_RESOURCE, "이미 동일한 상품에 입찰하셨습니다.");

    private final ErrorCode errorCode;
    private final String detailMessage;

    AuctionErrorCode(ErrorCode errorCode, String detailMessage) {
        this.errorCode = errorCode;
        this.detailMessage = detailMessage;
    }

    public BusinessException toException() {
        return new BusinessException(errorCode, detailMessage);
    }

}
