package com.example.sales_management.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/index")
    public String chuyentrangIndex() {
        return "index";
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
    @GetMapping("/chuyentranginstruct")
    public String chuyentranginstruct() {
        return "instruct";
    }
    @GetMapping("/chuyentrangnotification")
    public String chuyentrangnotification() {
        return "notification";
    }
}
