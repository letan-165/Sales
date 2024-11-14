package com.example.sales_management.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.sales_management.Models.Discount;
import com.example.sales_management.Models.DiscountProduct;
import com.example.sales_management.Models.Import;
import com.example.sales_management.Models.ImportProduct;
import com.example.sales_management.Models.Product;
import com.example.sales_management.Services.DiscountService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/discount")
@RequiredArgsConstructor
public class DiscountController {
    private final DiscountService discountService;

    @GetMapping("/list")
    public String getDiscounts(Model model) {
        List<Discount> discounts = discountService.findAll();
        
        Map<String, Long> totalQuantity = new HashMap<>();
        for (Discount discount : discounts) {
            Long quantity = discountService.geQuantityDiscount(discount.getDiscountID());
            totalQuantity.put(discount.getDiscountID(), quantity);
        }
        model.addAttribute("discounts", discountService.findAll());
        model.addAttribute("totalQuantity", totalQuantity);
        return "discounts"; 
    }

    @PostMapping("/add")
    public String addDiscount(@ModelAttribute Discount discount) {
        discountService.save(discount);
        return "redirect:/discount/list";
    }

    @PostMapping("/edit/{id}")
    public String editDiscount(@PathVariable String id, Model model) {
        Discount discount = discountService.findById(id);
        model.addAttribute("promotion", discount);
        return "discounts"; 
    }

    @PostMapping("/delete/{id}")
    public String deleteDiscount(@PathVariable String id) {
        Discount discount = discountService.findById(id);
        if(discount!=null){
            discountService.deleteDiscountProductsByDiscountID(id);
            discountService.deleteById(id);
        }
        discountService.deleteById(id);
        return "redirect:/discount/list";
    }

    @RequestMapping(value = "/list/listDiscountProduct/{discountID}", method = { RequestMethod.GET, RequestMethod.POST })
    public String getdiscountProduct(Model model,@PathVariable String discountID, HttpSession session){           
        List<DiscountProduct> listDiscountProduct = discountService.findDiscountProductsByDiscountID(discountID);
        model.addAttribute("listDiscountProduct", listDiscountProduct);
        return "discountProduct";
    }
}
