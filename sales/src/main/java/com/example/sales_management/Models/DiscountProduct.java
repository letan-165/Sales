package com.example.sales_management.Models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "discount_products")
@Data
@AllArgsConstructor
@NoArgsConstructor 
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
public class DiscountProduct {
    
    @EmbeddedId
    private DiscountProductId id;

    @ManyToOne
    @MapsId("discountID")
    @JoinColumn(name = "discountID")
    private Discount discounts;

    @ManyToOne
    @MapsId("productID")
    @JoinColumn(name = "productID")
    private Product products;

    private Long quantity;
}
