package com.example.sales_management.Controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.sales_management.Models.Discount;
import com.example.sales_management.Services.DiscountService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/discounts")
@RequiredArgsConstructor
public class DiscountController {
    private final DiscountService discountService;

    // Display all discounts
    @GetMapping("/list")
    public String getAllDiscounts(Model model) {
        List<Discount> discounts = discountService.findAll();
        model.addAttribute("discounts", discounts);
        return "discounts"; // The main view displaying discounts
    }

    // Display a discount by ID
    @GetMapping("/view/{id}")
    public String getDiscountById(@PathVariable String id, Model model) {
        Discount discount = discountService.findById(id);
        model.addAttribute("discount", discount);
        return "discountDetails"; // Single discount details view
    }

    // Show form to add a new discount
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("discount", new Discount());
        return "discountForm"; // Form for creating a new discount
    }

    // Create or update a discount
    @PostMapping("/save")
    public String createOrUpdateDiscount(@ModelAttribute Discount discount) {
        discountService.save(discount);
        return "redirect:/discounts/list";
    }

    // Show edit form for an existing discount
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Discount discount = discountService.findById(id);
        model.addAttribute("discount", discount);
        return "discountForm"; // Reuse the form view for editing
    }

    // Delete a discount
    @GetMapping("/delete/{id}")
    public String deleteDiscount(@PathVariable String id) {
        discountService.deleteById(id);
        return "redirect:/discounts/list";
    }
}
