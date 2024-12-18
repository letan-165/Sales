package com.example.sales_management.Services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.sales_management.Models.Discount;
import com.example.sales_management.Models.Order;
import com.example.sales_management.Models.OrderProduct;
import com.example.sales_management.Models.OrderProductId;
import com.example.sales_management.Repository.OrderProductRepository;
import com.example.sales_management.Repository.OrderRepository;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {
    OrderRepository orderRepository;
    OrderProductRepository orderProductRepository;

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

    public Order getFirst() {
        List<Order> order = orderRepository.findAll();
        return order.isEmpty() ? null : order.get(0);
    }
    public List<OrderProduct> findOrderProductsByOrderID(Long orderID) {
        return orderProductRepository.findByOrders_OrderID(orderID);
    }
    public Long getPriceOrder(Long orderID) {
        List<OrderProduct> orderProducts = findOrderProductsByOrderID(orderID);
        return orderProducts.stream()
                             .mapToLong(ip -> {
                                Discount discount = ip.getDiscount();
                                return discount != null 
                                       ? (ip.getProducts().getPrice() - (ip.getProducts().getPrice() * discount.getDiscount() / 100)) * ip.getQuantity() 
                                       : ip.getProducts().getPrice() * ip.getQuantity();
                                })
                             .sum();
    }
    public void saveOrderProduct(OrderProduct orderProduct) {
        orderProductRepository.save(orderProduct);
    }
    public void deleteOrderProductsByOrderID(Long orderID) {
        orderProductRepository.deleteByOrderID(orderID);
    }
    @Transactional
    public void deleteOrderProduct(Long orderID, Long productID) {
        OrderProductId orderProductId = new OrderProductId(orderID, productID);
        orderProductRepository.deleteById(orderProductId);
    }
    public List<OrderProduct> getProductsByDiscount(String discountID) {
        return orderProductRepository.findByDiscount_DiscountID(discountID);
    }
    public List<Discount> findDiscountsByProductID(Long price){
        return orderProductRepository.findDiscountsByProductID(price);
    }

     public List<OrderProduct> findOrderProductsByTimeRange(String startDate, String endDate) {
        try {
            LocalDateTime startDateTime = parseStartDate(startDate);
            LocalDateTime endDateTime = parseEndDate(endDate);
            return orderProductRepository.findOrderProductsByTimeRange(startDateTime, endDateTime);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Định dạng ngày không hợp lệ. Định dạng hợp lệ: yyyy-MM-dd.", e);
        }
    }
    
    private LocalDateTime parseStartDate(String startDate) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate localDate = LocalDate.parse(startDate, dateFormatter);
            return localDate.atStartOfDay();
        } catch (Exception e) {
            throw new IllegalArgumentException("Định dạng ngày không hợp lệ. Định dạng hợp lệ: yyyy-MM-dd.");
        }
    }
    
    private LocalDateTime parseEndDate(String endDate) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate localDate = LocalDate.parse(endDate, dateFormatter);
            return localDate.atTime(23, 59, 59);
        } catch (Exception e) {
            throw new IllegalArgumentException("Định dạng ngày không hợp lệ. Định dạng hợp lệ: yyyy-MM-dd.");
        }
    }
}
