package com.example.sales_management.Services;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.sales_management.Models.Import;
import com.example.sales_management.Models.ImportProduct;
import com.example.sales_management.Models.ImportProductId;
import com.example.sales_management.Models.Product;
import com.example.sales_management.Repository.ImportProductRepository;
import com.example.sales_management.Repository.ImportRepository;

class ImportServiceTest {

    @Mock
    ImportRepository importRepository;

    @Mock
    ImportProductRepository importProductRepository;

    @InjectMocks
    ImportService importService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        Import import1 = new Import(1L, "Paid", LocalDateTime.now(), "Supplier1", null, null);
        Import import2 = new Import(2L, "UnPaid", LocalDateTime.now(), "Supplier2", null, null);
        when(importRepository.findAll()).thenReturn(Arrays.asList(import1, import2));
        List<Import> imports = importService.findAll();
        assertNotNull(imports);
        assertEquals(2, imports.size());
    }

    @Test
    void findById() {
        Long importID = 1L;
        Import import1 = new Import(importID, "Paid", LocalDateTime.now(), "Supplier1", null, null);
        when(importRepository.findById(importID)).thenReturn(Optional.of(import1));
        Import result = importService.findById(importID);
        assertNotNull(result);
        assertEquals(importID, result.getImportID());
    }

    @Test
    void save() {
        Import import1 = new Import(null, "Paid", LocalDateTime.now(), "Supplier1", null, null);
        Import savedImport = new Import(1L, "Paid", LocalDateTime.now(), "Supplier1", null, null);
        when(importRepository.save(import1)).thenReturn(savedImport);
        Import result = importService.save(import1);
        assertNotNull(result);
        assertEquals(1L, result.getImportID());
    }

    @Test
    void deleteById() {
        Long importID = 1L;
        doNothing().when(importRepository).deleteById(importID);
        importService.deleteById(importID);
        verify(importRepository, times(1)).deleteById(importID);
    }

    @Test
    void findImportProductsByImportID() {
        Long importID = 1L;
        Import import1 = new Import(importID, "Paid", LocalDateTime.now(), "Supplier1", null, null);
        Product product1 = new Product(1L, "Product1", 100L, 50L, 10L, "Available", "Type1", "Description", null, null);
        Product product2 = new Product(2L, "Product2", 200L, 100L, 20L, "Available", "Type2", "Description", null, null);
        ImportProduct importProduct1 = new ImportProduct(new ImportProductId(importID, 1L), import1, product1, 5L);
        ImportProduct importProduct2 = new ImportProduct(new ImportProductId(importID, 2L), import1, product2, 10L);
        when(importProductRepository.findByImports_ImportID(importID)).thenReturn(Arrays.asList(importProduct1, importProduct2));
        List<ImportProduct> importProducts = importService.findImportProductsByImportID(importID);
        assertNotNull(importProducts);
        assertEquals(2, importProducts.size());
    }

    @Test
    void getPriceImport() {
        Long importID = 1L;
        Product product1 = new Product(1L, "Product1", 100L, 50L, 10L, "Available", "Type1", "Description", null, null);
        Product product2 = new Product(2L, "Product2", 200L, 100L, 20L, "Available", "Type2", "Description", null, null);
        ImportProduct importProduct1 = new ImportProduct(new ImportProductId(importID, 1L), null, product1, 5L);
        ImportProduct importProduct2 = new ImportProduct(new ImportProductId(importID, 2L), null, product2, 10L);
        when(importProductRepository.findByImports_ImportID(importID)).thenReturn(Arrays.asList(importProduct1, importProduct2));
        Long totalCost = importService.getPriceImport(importID);
        assertEquals(1250L, totalCost); // (5 * 50) + (10 * 100)
    }
}
