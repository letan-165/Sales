package com.example.sales_management.Models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor 
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderID;

    LocalDateTime orderTime;
    String status;

    @OneToMany(mappedBy = "orders")
    List<OrderProduct> orderProducts;

    @ManyToOne
    @JoinColumn(name = "userID")
    User user;

    @OneToOne
    @JoinColumn(name = "invoiceID")
    Invoice invoice;
    
    
}
