package com.sparta.limited.auction_service.auction_product.domain.model;

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
@Table(name = "p_auction_product")
public class AuctionProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID productId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(precision = 10, scale = 2, nullable = true)
    private BigDecimal price;

    @Column(nullable = false)
    private int quantity;

    private AuctionProduct(UUID productId, String title, String description, BigDecimal price) {
        this.productId = productId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.quantity = 1;
    }

    public static AuctionProduct of(UUID productId, String title, String description,
        BigDecimal price) {
        return new AuctionProduct(productId, title, description, price);
    }

}
