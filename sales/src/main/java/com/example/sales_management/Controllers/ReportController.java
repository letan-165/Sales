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
import com.example.sales_management.Models.Invoice;
import com.example.sales_management.Models.OrderProduct;
import com.example.sales_management.Services.ImportService;
import com.example.sales_management.Services.InvoiceService;
import com.example.sales_management.Services.OrderService;


@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ImportService importService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private InvoiceService invoiceService;



    @GetMapping
    public String showReportPage() {
        return "reports"; 
    }

    @GetMapping("/generate")
    public String generateReport(
            @RequestParam String reportType, 
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") String startDate,  
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") String endDate,  
            Model model) {
        try {
            if ("import".equalsIgnoreCase(reportType)) {
                List<ImportProduct> reportData = importService.findImportProductsByTimeRange(startDate, endDate);
                model.addAttribute("reportType", "import");
                if (reportData.isEmpty()) {
                    model.addAttribute("noDataMessage", "Không có dữ liệu báo cáo cho khoảng thời gian này.");
                } else {
                    model.addAttribute("reportData", reportData);
                }
            } else if ("order".equalsIgnoreCase(reportType)) {
                List<OrderProduct> reportData = orderService.findOrderProductsByTimeRange(startDate, endDate);
                model.addAttribute("reportType", "order");
                if (reportData.isEmpty()) {
                    model.addAttribute("noDataMessage", "Không có dữ liệu báo cáo cho khoảng thời gian này.");
                } else {
                    model.addAttribute("reportData", reportData);
                }
            } else if ("invoice".equalsIgnoreCase(reportType)){
                List<Invoice> reportData = invoiceService.findInvoiceProductsByTimeRange(startDate, endDate);
                model.addAttribute("reportType", "invoice");
                if (reportData.isEmpty()) {
                    model.addAttribute("noDataMessage", "Không có dữ liệu báo cáo cho khoảng thời gian này.");
                } else {
                    model.addAttribute("reportData", reportData);
                }
            }
            else {
                model.addAttribute("errorMessage", "Loại báo cáo không hợp lệ.");
            }

            return "reports";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Lỗi: " + e.getMessage());
            return "reports";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Lỗi khi tạo báo cáo: " + e.getMessage());
            return "reports";
        }
    }
}
