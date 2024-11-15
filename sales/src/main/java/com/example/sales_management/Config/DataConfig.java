package com.example.sales_management.Config;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ResourceUtils;

import com.example.sales_management.Data.UserPremissionDataConfig;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DataConfig {
    UserPremissionDataConfig userPremissionDataConfig;
    JdbcTemplate jdbcTemplate;
    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            userPremissionDataConfig.createUsers();
            userPremissionDataConfig.createPermissions();
            userPremissionDataConfig.createRoles();
            // executeSqlFromFile("sql/discount.sql");
            // executeSqlFromFile("sql/imports.sql");
            // executeSqlFromFile("sql/invoice.sql");
            // executeSqlFromFile("sql/orders.sql");
            // executeSqlFromFile("sql/products.sql");
            // executeSqlFromFile("sql/reports.sql");
            // executeSqlFromFile("sql/manytomany/discount_products.sql");
            // executeSqlFromFile("sql/manytomany/imports_products.sql");
            // executeSqlFromFile("sql/manytomany/orders_products.sql");
        };
    }
    private void executeSqlFromFile(String filePath) {
        try {
            File sqlFile = ResourceUtils.getFile("classpath:" + filePath);
            String sql = Files.lines(Paths.get(sqlFile.getAbsolutePath()), StandardCharsets.UTF_8).collect(Collectors.joining("\n"));
            jdbcTemplate.execute(sql);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
