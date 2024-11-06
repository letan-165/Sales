package com.example.sales_management.Data;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Configuration;

import com.example.sales_management.Models.Import;
import com.example.sales_management.Services.ImportService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImportDataConfig {
    ImportService importService; 
    String[][] imports = {
        {"Supplier A", "nopay"},
        {"Supplier B", "nopay"},
        {"Supplier C", "nopay"},
        {"Supplier D", "nopay"},
        {"Supplier E", "nopay"}
    };

    public  void createImports() {
        for (String[] data : imports) {
            String supplier = data[0];
            if (importService.findBySupplier(supplier) == null) { 
                Import import_ = Import.builder()
                        .supplier(supplier)
                        .status(data[1])
                        .time(LocalDateTime.now())
                        .user(null)
                        .importProducts(null)
                        .build();
                importService.save(import_);
            }
        }
    }
}
