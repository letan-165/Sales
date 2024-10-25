package com.example.sales_management.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sales_management.Models.Order;
import com.example.sales_management.Repository.OrderRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long orderID) {
        return orderRepository.findById(orderID).orElse(null);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public void deleteById(Long orderID) {
        orderRepository.deleteById(orderID);
    }
}
