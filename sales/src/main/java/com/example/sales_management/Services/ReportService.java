package com.example.sales_management.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sales_management.Models.Report;
import com.example.sales_management.Repository.ReportRepository;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Report saveReport(Report report) {
        return reportRepository.save(report);
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public Optional<Report> getReportById(Integer reportID) {
        return reportRepository.findById(reportID);
    }

    public void deleteReport(Integer reportID) {
        reportRepository.deleteById(reportID);
    }
}
