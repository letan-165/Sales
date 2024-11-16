package com.example.sales_management.Models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
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
@Table(name = "discounts")
@Data
@AllArgsConstructor
@NoArgsConstructor 
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)

public class Discount {
    @Id
    String discountID;
    Integer discount;
    Long quantity;
    LocalDateTime startTime;
    LocalDateTime endTime;
    Long minimum;

    @OneToMany(mappedBy = "discount")
    List<OrderProduct> orderProduct;

    @ManyToOne
    @JoinColumn(name = "userID")
    User user;

}
