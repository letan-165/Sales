package com.example.sales_management.Services;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.sales_management.Models.Invoice;
import com.example.sales_management.Repository.InvoiceRepository;

class InvoiceServiceTest {

    @Mock
    InvoiceRepository invoiceRepository;

    @InjectMocks
    InvoiceService invoiceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        Invoice invoice1 = new Invoice(1L, LocalDateTime.now(), 10000L, "Paid", "Invoice 1", null);
        Invoice invoice2 = new Invoice(2L, LocalDateTime.now(), 20000L, "Unpaid", "Invoice 2", null);
        when(invoiceRepository.findAll()).thenReturn(Arrays.asList(invoice1, invoice2));
        
        List<Invoice> invoices = invoiceService.findAll();
        assertNotNull(invoices);
        assertEquals(2, invoices.size());
    }

    @Test
    void findById() {
        Long invoiceID = 1L;
        Invoice invoice = new Invoice(invoiceID, LocalDateTime.now(), 10000L, "Paid", "Invoice 1", null);
        when(invoiceRepository.findById(invoiceID)).thenReturn(Optional.of(invoice));

        Invoice result = invoiceService.findById(invoiceID);
        assertNotNull(result);
        assertEquals(invoiceID, result.getInvoiceID());
    }

    @Test
    void saveOrUpdate() {
        Invoice invoice = new Invoice(null, LocalDateTime.now(), 10000L, "Paid", "Invoice 1", null);
        Invoice savedInvoice = new Invoice(1L, LocalDateTime.now(), 10000L, "Paid", "Invoice 1", null);
        when(invoiceRepository.save(invoice)).thenReturn(savedInvoice);

        Invoice result = invoiceService.saveOrUpdate(invoice);
        assertNotNull(result);
        assertEquals(1L, result.getInvoiceID());
    }

    @Test
    void deleteById() {
        Long invoiceID = 1L;
        when(invoiceRepository.existsById(invoiceID)).thenReturn(true);
        doNothing().when(invoiceRepository).deleteById(invoiceID);

        invoiceService.deleteById(invoiceID);
        verify(invoiceRepository, times(1)).deleteById(invoiceID);
    }

    @Test
    void findByStatus() {
        String status = "Paid";
        Invoice invoice1 = new Invoice(1L, LocalDateTime.now(), 10000L, "Paid", "Invoice 1", null);
        Invoice invoice2 = new Invoice(2L, LocalDateTime.now(), 20000L, "Paid", "Invoice 2", null);
        when(invoiceRepository.findByStatus(status)).thenReturn(Arrays.asList(invoice1, invoice2));

        List<Invoice> invoices = invoiceService.findByStatus(status);
        assertNotNull(invoices);
        assertEquals(2, invoices.size());
        assertTrue(invoices.stream().allMatch(i -> i.getStatus().equals(status)));
    }

    @Test
    void findByInvoiceTimeBetween() {
        LocalDateTime start = LocalDateTime.now().minusDays(10);
        LocalDateTime end = LocalDateTime.now();
        Invoice invoice1 = new Invoice(1L, LocalDateTime.now().minusDays(5), 10000L, "Paid", "Invoice 1", null);
        Invoice invoice2 = new Invoice(2L, LocalDateTime.now().minusDays(3), 20000L, "Paid", "Invoice 2", null);
        when(invoiceRepository.findByInvoiceTimeBetween(start, end)).thenReturn(Arrays.asList(invoice1, invoice2));

        List<Invoice> invoices = invoiceService.findByInvoiceTimeBetween(start, end);
        assertNotNull(invoices);
        assertEquals(2, invoices.size());
    }

    @Test
    void findByTotalAmountGreaterThanEqual() {
        Long totalAmount = 15000L;
        Invoice invoice1 = new Invoice(1L, LocalDateTime.now(), 20000L, "Paid", "Invoice 1", null);
        Invoice invoice2 = new Invoice(2L, LocalDateTime.now(), 30000L, "Paid", "Invoice 2", null);
        when(invoiceRepository.findByTotalAmountGreaterThanEqual(totalAmount)).thenReturn(Arrays.asList(invoice1, invoice2));

        List<Invoice> invoices = invoiceService.findByTotalAmountGreaterThanEqual(totalAmount);
        assertNotNull(invoices);
        assertEquals(2, invoices.size());
        assertTrue(invoices.stream().allMatch(i -> i.getTotalAmount() >= totalAmount));
    }

    @Test
    void findInvoiceProductsByTimeRange() {
        String startDate = "2023-01-01";
        String endDate = "2023-12-31";
        LocalDateTime startDateTime = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2023, 12, 31, 23, 59, 59);
        Invoice invoice1 = new Invoice(1L, LocalDateTime.now().minusMonths(6), 20000L, "Paid", "Invoice 1", null);
        Invoice invoice2 = new Invoice(2L, LocalDateTime.now().minusMonths(3), 30000L, "Paid", "Invoice 2", null);

        when(invoiceRepository.findInvoiceProductsByTimeRange(startDateTime, endDateTime))
                .thenReturn(Arrays.asList(invoice1, invoice2));

        List<Invoice> invoices = invoiceService.findInvoiceProductsByTimeRange(startDate, endDate);
        assertNotNull(invoices);
        assertEquals(2, invoices.size());
    }
}
