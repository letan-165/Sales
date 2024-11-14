package com.example.sales_management.Data;

import java.text.ParseException;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.sales_management.Models.Permission;
import com.example.sales_management.Models.User;
import com.example.sales_management.Services.AuthenticationService;
import com.example.sales_management.Services.PermissionService;
import com.example.sales_management.Services.UserService;
import com.nimbusds.jose.JOSEException;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserPremissionDataConfig {
    AuthenticationService authenticationService;
    UserService userService;
    PermissionService permissionService;
    PasswordEncoder passwordEncoder;
    
    String[][] users = {
        {"admin", "adminName", "admin@","0123","ADMIN"},
        {"manager", "managerName", "manager@","0789","MANAGER"},
        {"warehouse", "warehouseName", "warehouse@","0789","WAREHOUSE"},
        {"accountant", "accountantName", "accountant@","0789","ACCOUNTANT"},
        {"sales", "salesName", "sales@","0789","SALES"},
        };
    String[] permissions = {
        "DISCOUNT_CREATE", "DISCOUNT_READ","DISCOUNT_UPDATE","DISCOUNT_DELETE",
        "IMPORT_CREATE", "IMPORT_READ", "IMPORT_UPDATE", "IMPORT_DELETE",
        "INVOICE_CREATE", "INVOICE_READ", "INVOICE_UPDATE", "INVOICE_DELETE",
        "ORDER_CREATE", "ORDER_READ", "ORDER_UPDATE", "ORDER_DELETE",
        "PRODUCT_CREATE", "PRODUCT_READ", "PRODUCT_UPDATE", "PRODUCT_DELETE",
        "REPORT_CREATE", "REPORT_READ", "REPORT_UPDATE", "REPORT_DELETE",
    };
    String[] roles = {
        "MANAGER","WAREHOUSE","ACCOUNTANT","SALES",
    };
    String[] managerRoles = {
        "REPORT_CREATE", "REPORT_READ", "REPORT_UPDATE", "REPORT_DELETE",
        "DISCOUNT_CREATE", "DISCOUNT_READ","DISCOUNT_UPDATE","DISCOUNT_DELETE","IMPORT_READ","INVOICE_READ","PRODUCT_READ"
    };
    
    String[] warehouseRoles = {
        "IMPORT_CREATE", "IMPORT_READ", "IMPORT_UPDATE", "IMPORT_DELETE","PRODUCT_READ"
    };
    
    String[] accountantRoles = {
        "INVOICE_CREATE", "INVOICE_READ", "INVOICE_UPDATE", "INVOICE_DELETE",
    };
    
    String[] salesRoles = {
        "ORDER_CREATE", "ORDER_READ", "ORDER_UPDATE", "ORDER_DELETE","PRODUCT_READ"
    };
    
    public void createUsers() {
        for (String[] userData : users) {
            String userID = userData[0];
            if (userService.findById(userID)==null) {
                User user = User.builder()
                        .userID(userID)
                        .userName(userData[1])
                        .passWord(passwordEncoder.encode(userData[0]))
                        .email(userData[2])
                        .phone(userData[3])
                        .role(userData[4])
                        .build();
                userService.save(user);
            }
        }
    }
    public void createPermissions() throws JOSEException, ParseException {
        for (String permissionName : permissions) {
            if (permissionService.findById(permissionName)==null) {
                Permission permission = Permission.builder()
                        .permissionID(permissionName)
                        .description(permissionName)
                        .build();
                permissionService.save(permission);
            }
        }
    }
    @Transactional
    public void addPermission(String role, String[] rolePermissions) {
        List<User> users = userService.findByRole(role);
        List<Permission> permissions = permissionService.findAllById(List.of(rolePermissions));
        for (User user : users) {
            for (Permission permission : permissions) {
                if (!user.getPermissions().contains(permission)) {
                    user.getPermissions().add(permission);
                }
            }
            userService.save(user);
        }
    }
    @Transactional
    public void createRoles() {
        addPermission("ADMIN", permissions);
        addPermission("MANAGER", managerRoles);
        addPermission("WAREHOUSE", warehouseRoles);
        addPermission("ACCOUNTANT", accountantRoles);
        addPermission("SALES", salesRoles);
    }
    
}


