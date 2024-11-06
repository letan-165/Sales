package com.example.sales_management.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sales_management.Models.Import;

@Repository
public interface ImportRepository extends JpaRepository<Import, Long> {
    Optional<Import> findBySupplier(String supplier);
}
