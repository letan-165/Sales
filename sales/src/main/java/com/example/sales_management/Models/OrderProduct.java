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
@Table(name = "order_products")
@Data
@AllArgsConstructor
@NoArgsConstructor 
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
public class OrderProduct {
    
    @EmbeddedId
    OrderProductId id;

    @ManyToOne
    @MapsId("orderID")
    @JoinColumn(name = "orderID")
    Order orders;

    @ManyToOne
    @MapsId("productID")
    @JoinColumn(name = "productID")
    Product products;

    Long quantity;
}
