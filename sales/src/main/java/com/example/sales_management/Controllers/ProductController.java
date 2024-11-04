package com.example.sales_management.Controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.sales_management.Models.Product;
import com.example.sales_management.Services.ProductService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;



@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductService productService;

    @GetMapping("/list")
    public String getProdusts(Model model){
        model.addAttribute("list",productService.findAll());
        return "products";
    }
    
    @PostMapping ("/add")
    public String addProdust(@ModelAttribute Product product) {
        productService.save(product);
        return "redirect:/product/list";
    }
    @PostMapping ("/edit/{id}")
    public String ediProdust(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "products";
    }
    
    @PostMapping ("/delete/{id}")
    public String deleteProdust(@PathVariable  Long id) {
        productService.deleteById(id);
        return "redirect:/product/list";
    }
    
}
