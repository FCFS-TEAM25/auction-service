package com.sparta.limited.auction_service.auction.domain.validator;

import com.sparta.limited.auction_service.auction.domain.exception.AuctionErrorCode;
import com.sparta.limited.auction_service.auction.domain.model.Auction;
import com.sparta.limited.auction_service.auction.domain.model.AuctionStatus;
import com.sparta.limited.auction_service.auction.domain.repository.AuctionBidRepository;
import com.sparta.limited.auction_service.auction.domain.repository.AuctionRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuctionValidator {

    private final AuctionBidRepository auctionBidRepository;
    private final AuctionRepository auctionRepository;

    public void validateAuction(Auction auction) {
        LocalDateTime now = LocalDateTime.now();

        // 경매 마감 시간
        if (auction.getEndTime().isBefore(now)) {
            throw AuctionErrorCode.AUCTION_CLOSED.toException();
        }

        // 경매 시작 시간
//        if (auction.getStartTime().isAfter(now)) {
//            throw AuctionErrorCode.AUCTION_NOT_STARTED.toException();
//        }

        // 경매 상태 확인 (진행 중인 경매여야 함 -> AuctionStatus == ACTIVE)
        if (auction.getStatus() != AuctionStatus.ACTIVE) {
            throw AuctionErrorCode.AUCTION_NOT_ACTIVE.toException();
        }
    }

    public void validateBidPrice(Auction auction, BigDecimal bidPrice) {
        // 시작가 확인
        if (bidPrice.compareTo(auction.getStartingBid()) < 0) {
            throw AuctionErrorCode.BID_BELOW_STARTING_PRICE.toException();
        }
    }

    public void validateNoDuplicateBid(UUID auctionId, Long userId) {
        // 중복 입찰 확인
        if (auctionBidRepository.existsByAuctionIdAndUserId(auctionId, userId)) {
            throw AuctionErrorCode.DUPLICATE_BID.toException();
        }
    }

    public void validateWinner(UUID auctionId, Long userId) {
        // 사용자가 해당 경매의 낙찰자인지 확인
        if (!auctionRepository.existsByIdAndUserId(auctionId, userId)) {
            throw AuctionErrorCode.USER_NOT_WINNER.toException();
        }
    }

    public void validateAuctionForOrder(Auction auction) {
        // 경매 낙찰자 주문 생성용 경매 상태 확인 (경매 낙찰자 구매는 경매종료된 경매여야 함 -> AuctionStatus == CLOSED)
        if (auction.getStatus() != AuctionStatus.CLOSED) {
            throw AuctionErrorCode.AUCTION_NOT_CLOSED.toException();
        }
    }

    public static void validateAuctionDates(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.isBefore(LocalDateTime.now())) {
            throw AuctionErrorCode.INVALID_START_TIME.toException();
        }
        if (startTime.isAfter(endTime)) {
            throw AuctionErrorCode.INVALID_END_TIME.toException();
        }
    }

    public static void validateStatus(AuctionStatus status) {
        AuctionStatus.validateClosedStatus(status);
    }
}
