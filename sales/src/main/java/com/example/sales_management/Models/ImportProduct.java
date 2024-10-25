package com.example.sales_management.Models;

import jakarta.persistence.Column;
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
@Table(name = "imports_products")
@Data
@AllArgsConstructor
@NoArgsConstructor 
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
public class ImportProduct {

    @EmbeddedId
    private ImportProductId id;

    @ManyToOne
    @MapsId("importID")
    @JoinColumn(name = "importID")
    private Import imports;

    @ManyToOne
    @MapsId("productID")
    @JoinColumn(name = "productID")
    private Product products;

    @Column(name = "quantity")
    private Long quantity;

}

