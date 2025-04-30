package com.sparta.limited.auction_service.auction.domain.validator;

import com.sparta.limited.auction_service.auction.domain.exception.AuctionErrorCode;
import com.sparta.limited.common_module.exception.BusinessException;
import com.sparta.limited.common_module.exception.ErrorCode;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LuaScriptResultHandler {

    public void resultHandler(Long result, UUID auctionId) {
        if (result == null) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER, "경매 ID: " + auctionId + "에 대한 Lua 스크립트 실행이 실패했습니다.");
        }

        int resultCode = result.intValue();

        if (resultCode == 0) {
        } else if (resultCode == 1) {
            throw AuctionErrorCode.AUCTION_NOT_ACTIVE.toException();
        } else if (resultCode == 2) {
            throw AuctionErrorCode.BID_BELOW_STARTING_PRICE.toException();
        } else if (resultCode == 3) {
            throw AuctionErrorCode.DUPLICATE_BID.toException();
        } else {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER,
                "경매 ID: " + auctionId + "에 대한 입찰 처리 중 알 수 없는 오류가 발생했습니다. 오류 코드: " + resultCode);
        }
    }
}
