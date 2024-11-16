package com.example.sales_management.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.sales_management.Models.Discount;
import com.example.sales_management.Models.Order;
import com.example.sales_management.Models.OrderProduct;
import com.example.sales_management.Services.DiscountService;
import com.example.sales_management.Services.OrderService;

import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Controller
@RequestMapping("/discount")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DiscountController {
    DiscountService discountService;
    OrderService orderService;

    //Quyền đọc 
    @PreAuthorize("hasAnyRole('MANAGER', 'DISCOUNT_READ')")
    @GetMapping("/list")
    public String getDiscounts(Model model) {
        List<Discount> discounts = discountService.findAll();
        Map<String, Long> totalQuantitys = new HashMap<>();
        for (Discount discount : discounts) {
            Long totalQuantity = discountService.getTotalQuantity(discount.getDiscountID());
            totalQuantitys.put(discount.getDiscountID(), totalQuantity);
        }
        model.addAttribute("discounts", discounts);
        model.addAttribute("totalQuantitys", totalQuantitys);
        return "discounts"; 
    }
    //Quyền thêm
    @PreAuthorize("hasAnyRole('MANAGER', 'DISCOUNT_CREATE', 'DISCOUNT_UPDATE')")
    @PostMapping("/add")
    public String addDiscount(@ModelAttribute Discount discount) {
        discountService.save(discount);
        return "redirect:/discount/list";
    }
    //Quyền thêm
    @PreAuthorize("hasAnyRole('MANAGER', 'DISCOUNT_CREATE', 'DISCOUNT_UPDATE')")
    @PostMapping("/edit/{id}")
    public String editDiscount(@PathVariable String id, Model model) {
        Discount discount = discountService.findById(id);
        model.addAttribute("promotion", discount);
        return "discounts"; 
    }
    //Quyền xóa
    @PreAuthorize("hasAnyRole('MANAGER', 'DISCOUNT_DELETE')")
    @PostMapping("/delete/{id}")
    public String deleteDiscount(@PathVariable String id) {
        discountService.deleteById(id);
        return "redirect:/discount/list";
    }

    //Quyền xem các sản phẩm đã được áp dụng mã giảm
    @PreAuthorize("hasAnyRole('MANAGER', 'PRODUCT_READ')")
    @RequestMapping(value = "/list/listDiscountProduct/{discountID}", method = { RequestMethod.GET, RequestMethod.POST })
    public String getDiscountProduct(Model model,@PathVariable String discountID, HttpSession session){           
        List<OrderProduct> listDiscountProduct = orderService.getProductsByDiscount(discountID);
        model.addAttribute("listDiscountProduct", listDiscountProduct);
        return "discountProduct";
    }
}
