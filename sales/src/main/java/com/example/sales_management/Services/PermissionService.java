package com.example.sales_management.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sales_management.Models.Permission;
import com.example.sales_management.Repository.PermissionRepository;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository PermissionRepository;

    public List<Permission> findAll() {
        return PermissionRepository.findAll();
    }

    public Permission findById(Long PermissionId) {
        return PermissionRepository.findById(PermissionId).orElse(null);
    }

    public Permission save(Permission Permission) {
        return PermissionRepository.save(Permission);
    }

    public Permission update(Permission Permission) {
        return PermissionRepository.save(Permission);
    }

    public void deleteById(Long PermissionId) {
        PermissionRepository.deleteById(PermissionId);
    }
}
