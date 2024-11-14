package com.example.sales_management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


import com.example.sales_management.Models.DiscountProduct;
import com.example.sales_management.Models.DiscountProductId;

import jakarta.transaction.Transactional;

@Repository
public interface DiscountProductRepository extends JpaRepository<DiscountProduct, Long> {
    List<DiscountProduct> findByDiscounts_DiscountID(String discountID);
    @Modifying
    @Transactional
    @Query("DELETE FROM DiscountProduct ip WHERE ip.discounts.discountID = :discountID")
    void deleteByDiscountID(String discountID);
    void deleteById(DiscountProductId id);
}
