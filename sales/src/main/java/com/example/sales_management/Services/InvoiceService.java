package com.example.sales_management.Services;

import java.util.List;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.sales_management.Models.Invoice;
import com.example.sales_management.Repository.InvoiceRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvoiceService {
    InvoiceRepository invoiceRepository;
    //@PreAuthorize("hasRole('INVOICE_CREATE')")
    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    public Invoice findById(Long invoiceID) {
        return invoiceRepository.findById(invoiceID).orElse(null);
    }

    //@PreAuthorize("hasAnyRole('INVOICE_CREATE','INVOICE_UPDATE')")
    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    //@PreAuthorize("hasRole('INVOICE_READ')")
    public void deleteById(Long invoiceID) {
        invoiceRepository.deleteById(invoiceID);
    }
}
