package com.example.sales_management.Services;

import com.example.sales_management.Models.Invoice;
import com.example.sales_management.Repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    public Invoice findById(Integer invoiceID) {
        return invoiceRepository.findById(invoiceID).orElse(null);
    }

    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public void deleteById(Integer invoiceID) {
        invoiceRepository.deleteById(invoiceID);
    }
}
