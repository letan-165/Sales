package com.example.sales_management.Services;

import java.util.Arrays;
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

import com.example.sales_management.Models.Product;
import com.example.sales_management.Repository.ProductRepository;

class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        Product product1 = new Product();
        product1.setProductID(1L);
        Product product2 = new Product();
        product2.setProductID(2L);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));
        var products = productService.findAll();
        assertNotNull(products);
        assertEquals(2, products.size());
    }

    @Test
    void findById() {
        Long productID = 1L;
        Product product = new Product();
        product.setProductID(productID);
        when(productRepository.findById(productID)).thenReturn(Optional.of(product));
        var result = productService.findById(productID);
        assertNotNull(result);
        assertEquals(productID, result.getProductID());
    }

    @Test
    void save() {
        Product product = new Product();
        product.setProductID(1L);
        product.setProductName("Product 1");
        when(productRepository.save(product)).thenReturn(product);
        var savedProduct = productService.save(product);
        assertNotNull(savedProduct);
        assertEquals("Product 1", savedProduct.getProductName());
    }

    @Test
    void deleteById() {
        Long productID = 1L;
        doNothing().when(productRepository).deleteById(productID);
        productService.deleteById(productID);
        verify(productRepository, times(1)).deleteById(productID);
    }

    @Test
    void findByName() {
        String productName = "Product 1";
        Product product = new Product();
        product.setProductName(productName);
        when(productRepository.findByProductName(productName)).thenReturn(product);
        var result = productService.findByName(productName);
        assertNotNull(result);
        assertEquals(productName, result.getProductName());
    }
}
