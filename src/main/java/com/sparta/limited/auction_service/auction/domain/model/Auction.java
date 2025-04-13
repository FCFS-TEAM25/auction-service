package com.sparta.limited.auction_service.auction.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_auction")
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuctionStatus status;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal startingBid;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal finalBid;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    private Auction(Long userId, AuctionStatus status, BigDecimal startingBid, BigDecimal finalBid,
        LocalDateTime startTime, LocalDateTime endTime) {
        this.userId = userId;
        this.status = status;
        this.startingBid = startingBid;
        this.finalBid = finalBid;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static Auction of(Long userId, AuctionStatus status, BigDecimal startingBid,
        BigDecimal finalBid, LocalDateTime startTime, LocalDateTime endTime) {
        return new Auction(userId, status, startingBid, finalBid, startTime, endTime);
    }

}
