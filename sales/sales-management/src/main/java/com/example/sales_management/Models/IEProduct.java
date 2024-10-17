package com.example.sales_management.Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ie_product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor 
public class IEProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ieproductID;
    
    private String type;

    @OneToMany(mappedBy = "ieproductID")
    private List<Product> products = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "warehouseID")
    private Warehouse warehouse;

    private LocalDateTime time;
}
