package com.example.sales_management.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sales_management.Models.ImportProduct;
import com.example.sales_management.Services.ImportService;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ImportService importService;

    @GetMapping
    public String showReportPage() {
        return "report"; 
    }

    @GetMapping("/generate")
    public String generateReport(
            @RequestParam String reportType,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") String startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") String endDate,
            Model model) {
    
        try {
            List<ImportProduct> reportData = importService.findImportProductsByTimeRange(reportType, startDate, endDate);
    
            if (reportData.isEmpty()) {
                model.addAttribute("noDataMessage", "Không có dữ liệu báo cáo cho khoảng thời gian này.");
            } else {
                model.addAttribute("reportData", reportData);
            }
            
            return "report";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Lỗi khi tạo báo cáo: " + e.getMessage());
            return "report";
        }
    }
    
}
