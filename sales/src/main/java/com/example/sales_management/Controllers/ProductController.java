package com.example.sales_management.Controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    
    
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteById(id);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return "redirect:/product/list";
    }
    // QUANTITY

    @RequestMapping(value ="/productQuantity/{productID}", method = { RequestMethod.GET, RequestMethod.POST })
    public String productQuantity(Model model,@PathVariable(required = false) Long productID) {
        Product product = productService.findById(productID);
        if(product==null){
            product = productService.getFirst();
        }
        model.addAttribute("infoProduct", product);
        model.addAttribute("list",productService.findAll());
        return "productQuantity";
    }
    
    
    @PostMapping ("/updateQuantity")
    public String updateQuantity(@ModelAttribute Product product) {
        Product productUpdate = productService.findById(product.getProductID());
        productUpdate.setQuantity(product.getQuantity());
        productService.save(productUpdate);
        return "redirect:/product/productQuantity/"+product.getProductID();
    }
    
}
