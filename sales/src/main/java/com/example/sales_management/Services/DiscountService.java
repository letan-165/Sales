package com.example.sales_management.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.sales_management.Models.Discount;
import com.example.sales_management.Models.OrderProduct;
import com.example.sales_management.Repository.DiscountRepository;
import com.example.sales_management.Repository.OrderProductRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DiscountService {
    DiscountRepository discountRepository;
    OrderProductRepository orderProductRepository;

    public List<Discount> findAll() {
        return discountRepository.findAll();
    }
    public Discount findById(String discountID) {
        return discountRepository.findById(discountID).orElse(null);
    }
    public Discount save(Discount discount) {
        return discountRepository.save(discount);
    }
    public void deleteById(String discountID) {
        discountRepository.deleteById(discountID);
    }
    public Long getTotalQuantity(String discountID) {
        List<OrderProduct> orderProducts = orderProductRepository.findByDiscount_DiscountID(discountID);
        return orderProducts.stream()
                            .mapToLong(OrderProduct::getQuantity)
                            .sum();
    }
}