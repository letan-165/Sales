package com.example.sales_management.Models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    Long productID;
    String productName;
    Long price;
    Long priceImport;
    Long quantity;
    String status;
    String type;
    String description;

    @OneToMany(mappedBy = "products", cascade = CascadeType.PERSIST, orphanRemoval = false)
    List<OrderProduct> orderProducts;

    @OneToMany(mappedBy = "products", cascade = CascadeType.PERSIST, orphanRemoval = false)
    List<ImportProduct> importProducts;

    
}
