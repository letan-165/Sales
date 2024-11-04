package com.example.sales_management.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.sales_management.Models.Report;
import com.example.sales_management.Repository.ReportRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReportService {
    ReportRepository reportRepository;
    //@PreAuthorize("hasAnyRole('REPORT_CREATE','REPORT_UPDATE')")
    public Report save(Report report) {
        return reportRepository.save(report);
    }

    //@PreAuthorize("hasRole('REPORT_READ')")
    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    public Optional<Report> findById(Long reportID) {
        return reportRepository.findById(reportID);
    }

    @PreAuthorize("hasRole('REPORT_DELETE')")
    public void deleteById(Long reportID) {
        reportRepository.deleteById(reportID);
    }
}
