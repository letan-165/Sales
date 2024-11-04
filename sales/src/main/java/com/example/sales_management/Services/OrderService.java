package com.example.sales_management.Services;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.sales_management.Models.Order;
import com.example.sales_management.Repository.OrderRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {
    OrderRepository orderRepository;
    //@PreAuthorize("hasRole('ORDER_READ')")
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long orderID) {
        return orderRepository.findById(orderID).orElse(null);
    }

    @PreAuthorize("hasAnyRole('ORDER_CREATE','ORDER_UPDATE')")
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @PreAuthorize("hasRole('ORDER_READ')")
    public void deleteById(Long orderID) {
        orderRepository.deleteById(orderID);
    }
}
