package com.example.sales_management.Models;

import java.util.ArrayList;
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
@Table(name = "warehouses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor 
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer warehouseID;

    @OneToMany(mappedBy = "warehouse")
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "warehouse")
    private List<IEProduct> eProducts;

    @OneToMany(mappedBy = "warehouse")
    private List<IEProduct> iProducts;

    private String location;
}
