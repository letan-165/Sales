package com.example.sales_management.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.sales_management.Models.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    // Tìm hóa đơn theo trạng thái
    List<Invoice> findByStatus(String status);

    // Tìm hóa đơn theo khoảng thời gian lập hóa đơn
    List<Invoice> findByInvoiceTimeBetween(LocalDateTime start, LocalDateTime end);

    // Tìm hóa đơn theo tổng tiền
    List<Invoice> findByTotalAmountGreaterThanEqual(Long totalAmount);

        @Query("""
        SELECT i
        FROM Invoice i
        WHERE i.invoiceTime BETWEEN :startDate AND :endDate
        """)
    List<Invoice> findInvoiceProductsByTimeRange(
            @Param("startDate") LocalDateTime startDate, 
            @Param("endDate") LocalDateTime endDate
    );

}
