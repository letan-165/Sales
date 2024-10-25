package com.example.sales_management.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.sales_management.Models.Permission;
import com.example.sales_management.Models.User;
import com.example.sales_management.Services.PermissionService;
import com.example.sales_management.Services.UserService;

@Component
public class UserPermissionData implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public void run(String... args) throws Exception {
        Permission p1 = new Permission(null, "listProduct", null);
        Permission p2 = new Permission(null, "listOrder", null);
        
        permissionService.save(p1);
        permissionService.save(p2);
        User u1 = new User();
        u1.setUserID("admin");
        u1.setUserName("adminName");
        u1.setPassWord("password1");
        u1.setEmail("admin@");
        u1.setPhone("0123");
        u1.setRole("admin");
        
        User u2 = new User();
        u2.setUserID("manager");
        u2.setUserName("managerName");
        u2.setPassWord("password2");
        u2.setEmail("manager@");
        u2.setPhone("0789");
        u2.setRole("manager");
    
        u1.getPermissions().add(p1);
        u1.getPermissions().add(p2);
        u2.getPermissions().add(p1);

        userService.save(u1);
        userService.save(u2);
    }
}
