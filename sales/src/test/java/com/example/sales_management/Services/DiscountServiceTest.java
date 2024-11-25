package com.example.sales_management.Services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.sales_management.Models.Discount;
import com.example.sales_management.Models.OrderProduct;
import com.example.sales_management.Repository.DiscountRepository;
import com.example.sales_management.Repository.OrderProductRepository;

class DiscountServiceTest {

    @Mock
    DiscountRepository discountRepository;

    @Mock
    OrderProductRepository orderProductRepository;

    @InjectMocks
    DiscountService discountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        Discount discount1 = new Discount();
        discount1.setDiscountID("D001");
        Discount discount2 = new Discount();
        discount2.setDiscountID("D002");
        when(discountRepository.findAll()).thenReturn(Arrays.asList(discount1, discount2));
        List<Discount> discounts = discountService.findAll();
        assertNotNull(discounts);
        assertEquals(2, discounts.size());
    }

    @Test
    void findById() {
        String discountID = "D001";
        Discount discount = new Discount();
        discount.setDiscountID(discountID);
        when(discountRepository.findById(discountID)).thenReturn(Optional.of(discount));
        Discount result = discountService.findById(discountID);
        assertNotNull(result);
        assertEquals(discountID, result.getDiscountID());
    }

    @Test
    void save() {
        Discount discount = new Discount();
        discount.setDiscountID("D001");
        when(discountRepository.save(discount)).thenReturn(discount);
        Discount result = discountService.save(discount);
        assertNotNull(result);
        assertEquals("D001", result.getDiscountID());
    }

    @Test
    void deleteById() {
        String discountID = "D001";
        doNothing().when(discountRepository).deleteById(discountID);
        discountService.deleteById(discountID);
        verify(discountRepository, times(1)).deleteById(discountID);
    }

    @Test
    void getTotalQuantity() {
        String discountID = "D001";
        OrderProduct orderProduct1 = new OrderProduct();
        orderProduct1.setQuantity(10L);
        OrderProduct orderProduct2 = new OrderProduct();
        orderProduct2.setQuantity(20L);
        when(orderProductRepository.findByDiscount_DiscountID(discountID)).thenReturn(Arrays.asList(orderProduct1, orderProduct2));
        Long totalQuantity = discountService.getTotalQuantity(discountID);
        assertEquals(30L, totalQuantity);
    }
}
