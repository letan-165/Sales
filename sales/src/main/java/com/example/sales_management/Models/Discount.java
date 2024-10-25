package com.example.sales_management.Models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
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
@Table(name = "discounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor 

public class Discount {
    @Id
    private String discountID;
    private Float discount;
    private Long quantity;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long minimum;

    @OneToMany(mappedBy = "discounts")
    private List<DiscountProduct> discountProducts;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;
}
