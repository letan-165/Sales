package com.example.sales_management.Models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor 
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
