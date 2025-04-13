package com.sparta.limited.auction_service.auction.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_auction_user")
public class AuctionUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID auctionId;

    @Column(nullable = false)
    private Long userId;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal bid;

    private AuctionUser(UUID auctionId, Long userId, BigDecimal bid) {
        this.auctionId = auctionId;
        this.userId = userId;
        this.bid = bid;
    }

    public static AuctionUser of(UUID auctionId, Long userId, BigDecimal bid) {
        return new AuctionUser(auctionId, userId, bid);
    }
    
}

