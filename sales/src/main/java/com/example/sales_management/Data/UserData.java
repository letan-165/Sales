package com.example.sales_management.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.sales_management.Models.User;
import com.example.sales_management.Services.UserService;

@Component
public class UserData implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        User u1 = User.builder()
                .userID("admin")
                .userName("adminName")
                .passWord("admin")
                .email("admin@")
                .phone("0123")
                .role("admin")
                .build();

        User u2 = User.builder()
                .userID("manager")
                .userName("managerName")
                .passWord("manager")
                .email("manager@")
                .phone("0789")
                .role("manager")
                .build();

        userService.save(u1);
        userService.save(u2);
    }
}