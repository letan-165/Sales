package com.example.sales_management.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sales_management.Models.Discount;
import com.example.sales_management.Repository.DiscountRepository;

@Service
public class DiscountService {
    @Autowired
    private DiscountRepository discountRepository;

    public List<Discount> findAll() {
        return discountRepository.findAll();
    }

    public Discount findById(Integer discountID) {
        return discountRepository.findById(discountID).orElse(null);
    }

    public Discount save(Discount discount) {
        return discountRepository.save(discount);
    }

    public void deleteById(Integer discountID) {
        discountRepository.deleteById(discountID);
    }
}
