package com.example.sales_management.Controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.sales_management.Models.Invoice;
import com.example.sales_management.Services.InvoiceService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvoiceController {
    InvoiceService invoiceService;

    // Lấy danh sách tất cả hóa đơn
    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        List<Invoice> invoices = invoiceService.findAll();
        return ResponseEntity.ok(invoices);
    }

    // Tìm hóa đơn theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable("id") Long invoiceID) {
        Invoice invoice = invoiceService.findById(invoiceID);
        return ResponseEntity.ok(invoice);
    }

    // Thêm mới hoặc cập nhật hóa đơn
    @PostMapping
    public ResponseEntity<Invoice> createOrUpdateInvoice(@RequestBody Invoice invoice) {
        Invoice savedInvoice = invoiceService.saveOrUpdate(invoice);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedInvoice);
    }

    // Xóa hóa đơn theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable("id") Long invoiceID) {
        invoiceService.deleteById(invoiceID);
        return ResponseEntity.noContent().build();
    }

    // Tìm hóa đơn theo trạng thái
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Invoice>> getInvoicesByStatus(@PathVariable("status") String status) {
        List<Invoice> invoices = invoiceService.findByStatus(status);
        return ResponseEntity.ok(invoices);
    }

    // Tìm hóa đơn trong khoảng thời gian
    @GetMapping("/time")
    public ResponseEntity<List<Invoice>> getInvoicesByTime(
            @RequestParam("start") String start,
            @RequestParam("end") String end) {
        LocalDateTime startTime = LocalDateTime.parse(start);
        LocalDateTime endTime = LocalDateTime.parse(end);
        List<Invoice> invoices = invoiceService.findByInvoiceTimeBetween(startTime, endTime);
        return ResponseEntity.ok(invoices);
    }

    // Tìm hóa đơn có tổng tiền lớn hơn hoặc bằng
    @GetMapping("/total/{amount}")
    public ResponseEntity<List<Invoice>> getInvoicesByTotalAmount(@PathVariable("amount") Long totalAmount) {
        List<Invoice> invoices = invoiceService.findByTotalAmountGreaterThanEqual(totalAmount);
        return ResponseEntity.ok(invoices);
    }
}
