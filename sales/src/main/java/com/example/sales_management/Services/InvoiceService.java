package com.example.sales_management.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sales_management.Models.Invoice;
import com.example.sales_management.Repository.InvoiceRepository;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    public Invoice findById(Long invoiceID) {
        return invoiceRepository.findById(invoiceID).orElse(null);
    }

    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public void deleteById(Long invoiceID) {
        invoiceRepository.deleteById(invoiceID);
    }
}
