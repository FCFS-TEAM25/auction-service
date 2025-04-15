package com.sparta.limited.auction_service.auction.domain.exception;

import com.sparta.limited.common_module.exception.BusinessException;
import com.sparta.limited.common_module.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum AuctionErrorCode {
    // 경매 정보 관련
    //AUCTION_NOT_FOUND(ErrorCode.RESOURCES_NOT_FOUND, "존재하지 않는 경매입니다."),
    AUCTION_CLOSED(ErrorCode.OPERATION_NOT_ALLOWED, "이미 마감된 경매입니다."),
    AUCTION_NOT_STARTED(ErrorCode.OPERATION_NOT_ALLOWED, "아직 시작되지 않은 경매입니다."),
    AUCTION_NOT_ACTIVE(ErrorCode.OPERATION_NOT_ALLOWED, "현재 진행 중인 경매가 아닙니다."),
    AUCTION_NOT_CLOSED(ErrorCode.OPERATION_NOT_ALLOWED, "경매가 아직 종료되지 않았습니다."),

    // 입찰 관련
    BID_BELOW_STARTING_PRICE(ErrorCode.INVALID_PARAMETER, "입찰가가 시작가보다 낮습니다."),
    DUPLICATE_BID(ErrorCode.DUPLICATE_RESOURCE, "이미 동일한 상품에 입찰하셨습니다."),
    NO_BIDS_FOUND(ErrorCode.RESOURCES_NOT_FOUND, "경매에 입찰 내역이 없습니다."),

    // 낙찰자 검증 관련
    USER_NOT_WINNER(ErrorCode.OPERATION_NOT_ALLOWED, "해당 경매의 낙찰자가 아닙니다."),

    // 스케줄링 관련 경매 시간
    AUCTION_ALREADY_CLOSED(ErrorCode.OPERATION_NOT_ALLOWED, "이미 종료된 경매입니다."),
    INVALID_START_TIME(ErrorCode.INVALID_PARAMETER, "시작일은 현재보다 이후여야 합니다."),
    INVALID_END_TIME(ErrorCode.INVALID_PARAMETER, "종료일은 시작일보다 이후여야 합니다.");

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
