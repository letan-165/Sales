package com.example.sales_management.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.sales_management.Models.Warehouse;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
}