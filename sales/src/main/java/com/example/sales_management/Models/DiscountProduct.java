package com.example.sales_management.Models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "discount_products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    private Integer quantity;
}
