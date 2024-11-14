package com.example.sales_management.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.sales_management.Models.ImportProduct;
import com.example.sales_management.Models.ImportProductId;

import jakarta.transaction.Transactional;

@Repository
public interface ImportProductRepository extends JpaRepository<ImportProduct, Long> {
    List<ImportProduct> findByImports_ImportID(Long importID);

    @Modifying
    @Transactional
    @Query("DELETE FROM ImportProduct ip WHERE ip.imports.importID = :importID")
    void deleteByImportID(Long importID);
    void deleteById(ImportProductId id);

        @Query("""
        SELECT ip 
        FROM ImportProduct ip
        JOIN ip.imports i
        WHERE i.time BETWEEN :startDate AND :endDate
        """)
    List<ImportProduct> findByImportTimeBetween(
            @Param("startDate") LocalDateTime startDate, 
            @Param("endDate") LocalDateTime endDate
    );
}
