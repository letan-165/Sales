package com.example.sales_management.Services;

import com.example.sales_management.Models.Product;
import com.example.sales_management.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Integer productID) {
        return productRepository.findById(productID).orElse(null);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Integer productID) {
        productRepository.deleteById(productID);
    }
}
