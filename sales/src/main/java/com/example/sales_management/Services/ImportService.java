package com.example.sales_management.Services;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.sales_management.Models.Import;
import com.example.sales_management.Repository.ImportRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImportService {
    ImportRepository importRepository;
    //@PreAuthorize("hasRole('IMPORT_READ')")
    public List<Import> findAll() {
        return importRepository.findAll();
    }
    public Import findById(Long ieProductID) {
        return importRepository.findById(ieProductID).orElse(null);
    }
    //@PreAuthorize("hasAnyRole('IMPORT_CREATE','IMPORT_UPDATE')")
    public Import save(Import ieProduct) {
        return importRepository.save(ieProduct);
    }
    //@PreAuthorize("hasRole('IMPORT_DELETE')")
    public void deleteById(Long ieProductID) {
        importRepository.deleteById(ieProductID);
    }
}
