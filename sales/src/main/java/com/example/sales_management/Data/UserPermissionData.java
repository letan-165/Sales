package com.example.sales_management.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.sales_management.Models.Permission;
import com.example.sales_management.Models.User;
import com.example.sales_management.Repository.PermissionRepository;
import com.example.sales_management.Repository.UserRepository;

@Component
public class UserPermissionData implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public void run(String... args) throws Exception {
        Permission p1 = new Permission(null, "listProduct", null);
        Permission p2 = new Permission(null, "listOrder", null);
        
        permissionRepository.save(p1);
        permissionRepository.save(p2);
        User u1 = new User("admin", "adminName", "password1", "admin@", "0123", "admin", null, null);
        User u2 = new User("manager", "managerName", "password2", "manager@", "0789", "manager", null, null);

    
        u1.getPermissions().add(p1);
        u1.getPermissions().add(p2);
        u2.getPermissions().add(p1);

        userRepository.save(u1);
        userRepository.save(u2);
    }
}
