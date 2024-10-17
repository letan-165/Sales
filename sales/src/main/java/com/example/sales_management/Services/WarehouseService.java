package com.example.sales_management.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sales_management.Models.Warehouse;
import com.example.sales_management.Repository.WarehouseRepository;

@Service
public class WarehouseService {
    @Autowired
    private WarehouseRepository warehouseRepository;

    public List<Warehouse> findAll() {
        return warehouseRepository.findAll();
    }

    public Warehouse findById(Integer warehouseID) {
        return warehouseRepository.findById(warehouseID).orElse(null);
    }

    public Warehouse save(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    public void deleteById(Integer warehouseID) {
        warehouseRepository.deleteById(warehouseID);
    }
}
