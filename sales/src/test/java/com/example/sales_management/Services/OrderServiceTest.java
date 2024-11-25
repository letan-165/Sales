package com.example.sales_management.Services;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.sales_management.Models.Discount;
import com.example.sales_management.Models.Order;
import com.example.sales_management.Models.OrderProduct;
import com.example.sales_management.Models.OrderProductId;
import com.example.sales_management.Models.Product;
import com.example.sales_management.Repository.OrderProductRepository;
import com.example.sales_management.Repository.OrderRepository;

class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    OrderProductRepository orderProductRepository;

    @InjectMocks
    OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        Order order1 = Order.builder().orderID(1L).status("Pending").build();
        Order order2 = Order.builder().orderID(2L).status("Completed").build();
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        List<Order> orders = orderService.findAll();
        assertNotNull(orders);
        assertEquals(2, orders.size());
    }

    @Test
    void findById() {
        Long orderID = 1L;
        Order order = Order.builder().orderID(orderID).status("Pending").build();
        when(orderRepository.findById(orderID)).thenReturn(Optional.of(order));

        Order result = orderService.findById(orderID);
        assertNotNull(result);
        assertEquals(orderID, result.getOrderID());
    }

    @Test
    void save() {
        Order order = Order.builder().status("Pending").build();
        Order savedOrder = Order.builder().orderID(1L).status("Pending").build();
        when(orderRepository.save(order)).thenReturn(savedOrder);

        Order result = orderService.save(order);
        assertNotNull(result);
        assertEquals(1L, result.getOrderID());
    }

    @Test
    void deleteById() {
        Long orderID = 1L;
        doNothing().when(orderRepository).deleteById(orderID);

        orderService.deleteById(orderID);
        verify(orderRepository, times(1)).deleteById(orderID);
    }

    @Test
    void findOrderProductsByOrderID() {
        Long orderID = 1L;
        OrderProduct orderProduct = OrderProduct.builder().quantity(2L).build();
        when(orderProductRepository.findByOrders_OrderID(orderID)).thenReturn(Arrays.asList(orderProduct));

        List<OrderProduct> orderProducts = orderService.findOrderProductsByOrderID(orderID);
        assertNotNull(orderProducts);
        assertEquals(1, orderProducts.size());
    }

    @Test
    void getPriceOrder() {
        Long orderID = 1L;
        Product product = Product.builder().price(100L).build();
        Discount discount = Discount.builder().discount(10).build();
        OrderProduct orderProduct = OrderProduct.builder()
                .products(product)
                .discount(discount)
                .quantity(2L)
                .build();

        when(orderProductRepository.findByOrders_OrderID(orderID)).thenReturn(Arrays.asList(orderProduct));

        Long totalPrice = orderService.getPriceOrder(orderID);
        assertEquals(180L, totalPrice);
    }

    @Test
    void findOrderProductsByTimeRange() {
        String startDate = "2023-01-01";
        String endDate = "2023-12-31";
        LocalDateTime startDateTime = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2023, 12, 31, 23, 59, 59);

        OrderProduct orderProduct = OrderProduct.builder().quantity(3L).build();
        when(orderProductRepository.findOrderProductsByTimeRange(startDateTime, endDateTime))
                .thenReturn(Arrays.asList(orderProduct));

        List<OrderProduct> orderProducts = orderService.findOrderProductsByTimeRange(startDate, endDate);
        assertNotNull(orderProducts);
        assertEquals(1, orderProducts.size());
    }

    @Test
    void deleteOrderProduct() {
        Long orderID = 1L;
        Long productID = 2L;
        OrderProductId orderProductId = new OrderProductId(orderID, productID);
        doNothing().when(orderProductRepository).deleteById(any(OrderProductId.class));
        orderService.deleteOrderProduct(orderID, productID);
        verify(orderProductRepository, times(1)).deleteById(orderProductId);
    }

}
