package com.example.sales_management.Config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.sales_management.Data.ProductDataConfig;
import com.example.sales_management.Data.UserPremissionDataConfig;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DataConfig {

    ProductDataConfig productDataConfig;
    UserPremissionDataConfig userPremissionDataConfig;
    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            userPremissionDataConfig.createUsers();
            userPremissionDataConfig.createPermissions();
            userPremissionDataConfig.createRoles();
            productDataConfig.createProducts();
        };
    }
}
