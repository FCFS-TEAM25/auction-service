package com.sparta.limited.auction_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.limited.auction_service.auction.application.dto.request.AuctionCreateBidRequest;
import com.sparta.limited.auction_service.auction.domain.model.Auction;
import com.sparta.limited.auction_service.auction.domain.repository.AuctionRepository;
import com.sparta.limited.auction_service.auction_product.domain.model.AuctionProduct;
import com.sparta.limited.auction_service.auction_product.domain.repository.AuctionProductRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@DisplayName("API: auction-service")
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class AuctionTest {

    private static final Logger logger = LoggerFactory.getLogger(AuctionTest.class);
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private AuctionProductRepository auctionProductRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private UUID productId;
    private Auction testAuction;
    private AuctionProduct testAuctionProduct;

    public AuctionTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();
        testAuctionProduct = AuctionProduct.of(
            productId,
            "테스트 상품",
            "테스트 상품 설명",
            BigDecimal.valueOf(10000)
        );

        auctionProductRepository.save(testAuctionProduct);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = now.plusMinutes(10);
        LocalDateTime endTime = now.plusHours(1);

        testAuction = Auction.of(
            testAuctionProduct.getProductId(),
            BigDecimal.valueOf(10000),
            startTime,
            endTime
        );

        auctionRepository.save(testAuction);

        jdbcTemplate.update(
            "UPDATE p_auction SET start_time = ?, status = 'ACTIVE' WHERE id = ?",
            java.sql.Timestamp.valueOf(now.minusMinutes(10)),
            testAuction.getId()
        );

        testAuction = auctionRepository.findById(testAuction.getId());

    }

    @Test
    @DisplayName("경매 입찰 생성")
    public void createAuctionBid() throws Exception {
        UUID auctionId = testAuction.getId();
        Long userId = 123L;
        BigDecimal bid = BigDecimal.valueOf(15000);

        AuctionCreateBidRequest request = AuctionCreateBidRequest.of(bid);

        // When
        ResultActions perform = mockMvc.perform(post("/api/v1/auctions/{auctionId}", auctionId)
            .header("X-User-Role", "ROLE_USER")
            .header("X-User-Id", userId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        );

        // Then
        perform.andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("auctionId").value(auctionId.toString()))
            .andExpect(jsonPath("userId").value(userId))
            .andExpect(jsonPath("bid").value(bid))
            .andExpect(header().exists(HttpHeaders.LOCATION));
    }

    @Test
    @DisplayName("경매 입찰 여러개 생성")
    public void createAuctionBids() throws Exception {
        UUID auctionId = testAuction.getId();

        int userCount = 3;
        List<Long> userIds = new ArrayList<>();
        List<BigDecimal> bids = new ArrayList<>();
        List<AuctionCreateBidRequest> requests = new ArrayList<>();

        for (int i = 0; i < userCount; i++) {
            userIds.add(100L + i);
            bids.add(BigDecimal.valueOf(15000));
            requests.add(AuctionCreateBidRequest.of(bids.get(i)));
        }

        for (int i = 0; i < userCount; i++) {
            // When
            ResultActions perform = mockMvc.perform(post("/api/v1/auctions/{auctionId}", auctionId)
                .header("X-User-Role", "ROLE_USER")
                .header("X-User-Id", userIds.get(i))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requests.get(i)))
            );

            // Then
            perform.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.auctionId").value(auctionId.toString()))
                .andExpect(jsonPath("$.userId").value(userIds.get(i)))
                .andExpect(jsonPath("$.bid").value(bids.get(i).intValue()))
                .andExpect(header().exists(HttpHeaders.LOCATION));
        }
    }
}
