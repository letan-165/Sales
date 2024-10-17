package com.example.sales_management.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sales_management.Models.IEProduct;
import com.example.sales_management.Repository.IEProductRepository;

@Service
public class IEProductService {
    @Autowired
    private IEProductRepository ieProductRepository;

    public List<IEProduct> findAll() {
        return ieProductRepository.findAll();
    }

    public IEProduct findById(Integer ieProductID) {
        return ieProductRepository.findById(ieProductID).orElse(null);
    }

    public IEProduct save(IEProduct ieProduct) {
        return ieProductRepository.save(ieProduct);
    }

    public void deleteById(Integer ieProductID) {
        ieProductRepository.deleteById(ieProductID);
    }
}
