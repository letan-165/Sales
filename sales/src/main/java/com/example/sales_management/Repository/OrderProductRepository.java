package com.example.sales_management.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.sales_management.Models.OrderProduct;
import com.example.sales_management.Models.OrderProductId;

import jakarta.transaction.Transactional;

public interface  OrderProductRepository extends JpaRepository<OrderProduct, Long>{
    List<OrderProduct> findByOrders_OrderID(Long orderID);

    @Modifying
    @Transactional
    @Query("DELETE FROM OrderProduct ip WHERE ip.orders.orderID = :orderID")
    void deleteByOrderID(Long orderID);
    void deleteById(OrderProductId id);
}
