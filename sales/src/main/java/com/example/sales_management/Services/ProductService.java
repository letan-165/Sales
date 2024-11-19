package com.example.sales_management.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.sales_management.Models.ImportProduct;
import com.example.sales_management.Models.Product;
import com.example.sales_management.Repository.ProductRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    ProductRepository productRepository;
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long productID) {
        return productRepository.findById(productID).orElse(null);
    }

    public Product save(Product product) {
        if(product.getStatus()==null){
            product.setStatus("new");
        }
        if(product.getQuantity()==null || product.getQuantity()==0){
            product.setQuantity(0l);  
            product.setStatus("outstock");
        }
        if(product.getStatus()!="new" || product.getQuantity()!=0){
            product.setStatus("instock");
        }
        return productRepository.save(product);
    }

    public void deleteById(Long productID) {
        productRepository.deleteById(productID);
    }
    public Product findByName(String productName) {
        return productRepository.findByProductName(productName);
    }
    public Product getFirst(List<ImportProduct> product) {
        return product.isEmpty() ? null : product.get(0).getProducts();
    }
    public Product getFirst() {
        List<Product> product = productRepository.findAll();
        return product.isEmpty() ? null : product.get(0);
    }
    
}
