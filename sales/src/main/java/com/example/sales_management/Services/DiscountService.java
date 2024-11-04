package com.example.sales_management.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.sales_management.Models.Discount;
import com.example.sales_management.Repository.DiscountRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DiscountService {
    DiscountRepository discountRepository;

    //@PreAuthorize("hasRole('DISCOUNT_READ')")
    public List<Discount> findAll() {
        return discountRepository.findAll();
    }
    public Discount findById(String discountID) {
        return discountRepository.findById(discountID).orElse(null);
    }
    //@PreAuthorize("hasAnyRole('DISCOUNT_CREATE','DISCOUNT_UPDATE')")
    public Discount save(Discount discount) {
        return discountRepository.save(discount);
    }
    //@PreAuthorize("hasRole('DISCOUNT_DELETE')")
    public void deleteById(String discountID) {
        discountRepository.deleteById(discountID);
    }
}
