package com.sparta.limited.auction_service;

import com.sparta.limited.auction_service.auction.application.dto.request.AuctionCreateBidRequest;
import com.sparta.limited.auction_service.auction.application.dto.response.AuctionCreateBidResponse;
import com.sparta.limited.auction_service.auction.application.service.AuctionServiceImpl;
import com.sparta.limited.auction_service.auction.domain.model.Auction;
import com.sparta.limited.auction_service.auction.domain.model.AuctionUser;
import com.sparta.limited.auction_service.auction.domain.repository.AuctionBidRepository;
import com.sparta.limited.auction_service.auction.domain.repository.AuctionRepository;
import com.sparta.limited.auction_service.auction.domain.validator.AuctionValidator;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisplayName("API: auction-serviceImpl")
@ExtendWith(MockitoExtension.class)
class AuctionServiceImplTest {

    private static final Logger logger = LoggerFactory.getLogger(AuctionBidThreadTest.class);

    @Mock
    private AuctionRepository auctionRepository;

    @Mock
    private AuctionBidRepository auctionBidRepository;

    @Mock
    private AuctionValidator auctionValidator;

    @InjectMocks
    private AuctionServiceImpl auctionService;

    private UUID auctionId;
    private Long userId;
    private BigDecimal bidPrice;
    private Auction auction;
    private AuctionUser auctionUser;

    @BeforeEach
    void setUp() {
        auctionId = UUID.randomUUID();
        userId = 1L;
        bidPrice = BigDecimal.valueOf(20000);

        auction = Auction.of(
            UUID.randomUUID(),
            BigDecimal.valueOf(10000),
            LocalDateTime.now().plusMinutes(10),
            LocalDateTime.now().plusHours(1)
        );

        auctionUser = AuctionUser.of(auctionId, userId, bidPrice);
    }

    @Test
    @DisplayName("경매 입찰 생성")
    void createMockAuctionBid() {
        // Given
        AuctionCreateBidRequest request = AuctionCreateBidRequest.of(bidPrice);

        when(auctionRepository.findById(auctionId)).thenReturn(auction);
        doNothing().when(auctionValidator).validateAuction(auction);
        doNothing().when(auctionValidator).validateNoDuplicateBid(auctionId, userId);
        doNothing().when(auctionValidator).validateBidPrice(auction, bidPrice);
        doNothing().when(auctionBidRepository).save(any(AuctionUser.class));

        // When
        AuctionCreateBidResponse response = auctionService.createAuctionBid(auctionId, userId, request);

        logger.info("AuctionId: " + response.getAuctionId() + ", " + "Bid: " + response.getBid() +
            ", " + "Id: " + response.getId() + ", " + "UserId: " +response.getUserId());

        // Then
        assertNotNull(response);
        assertEquals(auctionId, response.getAuctionId());
        assertEquals(userId, response.getUserId());
        assertEquals(bidPrice, response.getBid());
    }
}
