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

    public List<Permission> getAllPermissions() {
        return PermissionRepository.findAll();
    }

    public Permission getPermissionById(Long PermissionId) {
        return PermissionRepository.findById(PermissionId).orElse(null);
    }

    public Permission createPermission(Permission Permission) {
        return PermissionRepository.save(Permission);
    }

    public Permission updatePermission(Permission Permission) {
        return PermissionRepository.save(Permission);
    }

    public void deletePermission(Long PermissionId) {
        PermissionRepository.deleteById(PermissionId);
    }
}
