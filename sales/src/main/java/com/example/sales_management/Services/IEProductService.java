package com.example.sales_management.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sales_management.Models.Import;
import com.example.sales_management.Repository.IEProductRepository;

@Service
public class IEProductService {
    @Autowired
    private IEProductRepository ieProductRepository;

    public List<Import> findAll() {
        return ieProductRepository.findAll();
    }

    public Import findById(Integer ieProductID) {
        return ieProductRepository.findById(ieProductID).orElse(null);
    }

    public Import save(Import ieProduct) {
        return ieProductRepository.save(ieProduct);
    }

    public void deleteById(Integer ieProductID) {
        ieProductRepository.deleteById(ieProductID);
    }
}
