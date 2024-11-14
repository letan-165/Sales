package com.example.sales_management.Controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sales_management.Models.Invoice;
import com.example.sales_management.Services.InvoiceService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Controller
@RequestMapping("/invoice")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvoiceController {
    InvoiceService invoiceService;
    //Các quyền cơ bản
    @PreAuthorize("hasAnyRole('ACCOUNTANT', 'INVOICE_READ')")
    @GetMapping("/list")
    public String getAllInvoices(Model model) {
        List<Invoice> invoices = invoiceService.findAll();
        model.addAttribute("list",invoices);
        return "invoices";
    }
    @PreAuthorize("hasAnyRole('ACCOUNTANT', 'INVOICE_CREATE','INVOICE_UPDATE')")
    @PostMapping ("/add")
    public String addInvoice(@ModelAttribute Invoice invoice) {
        invoiceService.saveOrUpdate(invoice);
        return "redirect:/invoice/list";
    }

    @PreAuthorize("hasAnyRole('ACCOUNTANT', 'INVOICE_DELETE')")
    @PostMapping("delete/{id}")
    public String getInvoiceById(@PathVariable("id") Long invoiceID) {
        invoiceService.deleteById(invoiceID);
        return "redirect:/invoice/list";
    }
}
