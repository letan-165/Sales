package com.example.sales_management.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sales_management.Models.Discount;
import com.example.sales_management.Services.DiscountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/discounts")
@RequiredArgsConstructor
public class DiscountController {
    private final DiscountService discountService;

    // Get all discounts
    @GetMapping
    public ResponseEntity<List<Discount>> getAllDiscounts() {
        List<Discount> discounts = discountService.findAll();
        return ResponseEntity.ok(discounts);
    }

    // Get a discount by ID
    @GetMapping("/{id}")
    public ResponseEntity<Discount> getDiscountById(@PathVariable String id) {
        Discount discount = discountService.findById(id);
        if (discount != null) {
            return ResponseEntity.ok(discount);
        }
        return ResponseEntity.notFound().build();
    }

    // Create a new discount
    @PostMapping
    public ResponseEntity<Discount> createDiscount(@RequestBody Discount discount) {
        Discount savedDiscount = discountService.save(discount);
        return ResponseEntity.ok(savedDiscount);
    }

    // Update an existing discount
    @PutMapping("/{id}")
    public ResponseEntity<Discount> updateDiscount(@PathVariable String id, @RequestBody Discount discount) {
        Discount existingDiscount = discountService.findById(id);
        if (existingDiscount != null) {
            discount.setId(id); // Ensure the ID is set
            Discount updatedDiscount = discountService.save(discount);
            return ResponseEntity.ok(updatedDiscount);
        }
        return ResponseEntity.notFound().build();
    }

    // Delete a discount
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable String id) {
        discountService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}