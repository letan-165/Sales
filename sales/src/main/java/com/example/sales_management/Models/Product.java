package com.example.sales_management.Models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor 
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productID;
    private String productName;
    private Long price;
    private Long quantity;
    private String status;
    private String description;

    @OneToMany(mappedBy = "products")
    private List<OrderProduct> orderProducts;

    @OneToMany(mappedBy = "products")
    private List<DiscountProduct> discountProducts;

    @OneToMany(mappedBy = "products")
    private List<ImportProduct> importProducts;
    
}
