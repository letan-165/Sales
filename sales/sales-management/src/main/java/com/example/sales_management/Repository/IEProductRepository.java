package com.example.sales_management.Repository;

import com.example.sales_management.Models.IEProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEProductRepository extends JpaRepository<IEProduct, Integer> {
}
