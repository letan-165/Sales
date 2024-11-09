package com.example.sales_management.Services;

import java.util.List;

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

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }
    public void deleteById(Long orderID) {
        orderRepository.deleteById(orderID);
    }
    public Order update(Long id, Order order) {
        Order existingOrder = findById(id);
        existingOrder.setOrderTime(order.getOrderTime());
        existingOrder.setTotalAmount(order.getTotalAmount());
        existingOrder.setOrderDetail(order.getOrderDetail());
        return orderRepository.save(existingOrder);
    }
}