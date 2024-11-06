package com.example.sales_management.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/chuyentranginvoice")
    public String chuyentranginvoicemanager() {
        return "invoices";
    }
    @GetMapping("/chuyentrangorder")
    public String chuyentrangordermanager() {
        return "orders";
    }
    @GetMapping("/chuyentrangpromotion")
    public String chuyentrangpromotionmanager() {
        return "promotions";
    }
    @GetMapping("/chuyentrangreports")
    public String chuyentrangreportsmanager() {
        return "reports";
    }
    
    @GetMapping("/chuyentrangproductimex")
    public String chuyentrangproductimexmanager() {
        return "product-imexs";
    }
    @GetMapping("/chuyentrangfinancial")
    public String chuyentrangfinancialmanager() {
        return "financials";
    }
    @GetMapping("/chuyentranginstruct")
    public String chuyentranginstruct() {
        return "instruct";
    }
}
