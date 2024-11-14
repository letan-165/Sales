package com.example.sales_management.Services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.sales_management.Models.Invoice;
import com.example.sales_management.Repository.InvoiceRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvoiceService {
    InvoiceRepository invoiceRepository;

    // Lấy danh sách tất cả hóa đơn
    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    // Tìm hóa đơn theo ID
    public Invoice findById(Long invoiceID) {
        return invoiceRepository.findById(invoiceID)
                .orElseThrow(() -> new RuntimeException("Invoice not found with ID: " + invoiceID));
    }

    // Thêm mới hoặc cập nhật hóa đơn
    public Invoice saveOrUpdate(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    // Xóa hóa đơn theo ID
    public void deleteById(Long invoiceID) {
        if (!invoiceRepository.existsById(invoiceID)) {
            throw new RuntimeException("Invoice not found with ID: " + invoiceID);
        }
        invoiceRepository.deleteById(invoiceID);
    }

    // Tìm hóa đơn theo trạng thái
    public List<Invoice> findByStatus(String status) {
        return invoiceRepository.findByStatus(status);
    }

    // Tìm hóa đơn trong một khoảng thời gian
    public List<Invoice> findByInvoiceTimeBetween(LocalDateTime start, LocalDateTime end) {
        return invoiceRepository.findByInvoiceTimeBetween(start, end);
    }

    // Tìm hóa đơn có tổng tiền lớn hơn hoặc bằng một giá trị
    public List<Invoice> findByTotalAmountGreaterThanEqual(Long totalAmount) {
        return invoiceRepository.findByTotalAmountGreaterThanEqual(totalAmount);
    }
}
