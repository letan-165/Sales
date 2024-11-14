package com.example.sales_management.Services;

import java.util.List;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.example.sales_management.Models.Import;
import com.example.sales_management.Models.ImportProduct;
import com.example.sales_management.Models.ImportProductId;
import com.example.sales_management.Repository.ImportProductRepository;
import com.example.sales_management.Repository.ImportRepository;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImportService {
    ImportRepository importRepository;
    ImportProductRepository importProductRepository;
    public List<Import> findAll() {
        return importRepository.findAll();
    }
    public Import findById(Long importID) {
        return importRepository.findById(importID).orElse(null);
    }
    public Import save(Import import_) {
        return importRepository.save(import_);
    }
    public void deleteById(Long importID) {
        importRepository.deleteById(importID);
    }
    public Import findBySupplier(String supplier) {
        return importRepository.findBySupplier(supplier).orElse(null);
    }
    public Import getFirst() {
        List<Import> import_ = importRepository.findAll();
        return import_.isEmpty() ? null : import_.get(0);
    }

    public List<ImportProduct> findImportProductsByImportID(Long importID) {
        return importProductRepository.findByImports_ImportID(importID);
    }
    public void saveImportProduct(ImportProduct importProduct) {
        importProductRepository.save(importProduct);
    }
    public void deleteImportProductsByImportID(Long importID) {
        importProductRepository.deleteByImportID(importID);
    }

    public List<ImportProduct> findImportProductsByTimeRange(String reportType, String startDate, String endDate) {
        try {
            LocalDateTime startDateTime = parseStartDate(startDate);
            LocalDateTime endDateTime = parseEndDate(endDate);
            return importProductRepository.findByImportTimeBetween(startDateTime, endDateTime);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Định dạng yyyy-MM.", e);
        }
    }
    

    private LocalDateTime parseStartDate(String startDate) {
        DateTimeFormatter yearMonthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        try {
            YearMonth startYearMonth = YearMonth.parse(startDate, yearMonthFormatter);
            return startYearMonth.atDay(1).atStartOfDay();
        } catch (Exception e) {
            throw new IllegalArgumentException("Định dạng yyyy-MM.");
        }
    }

    private LocalDateTime parseEndDate(String endDate) {
        DateTimeFormatter yearMonthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        try {
            YearMonth endYearMonth = YearMonth.parse(endDate, yearMonthFormatter);
            return endYearMonth.atEndOfMonth().atTime(23, 59, 59);
        } catch (Exception e) {
            throw new IllegalArgumentException("Định dạng yyyy-MM.");
        }
    }

    @Transactional
    public void deleteImportProduct(Long importID, Long productID) {
        ImportProductId importProductId = new ImportProductId(importID, productID);
        importProductRepository.deleteById(importProductId);
    }
}
