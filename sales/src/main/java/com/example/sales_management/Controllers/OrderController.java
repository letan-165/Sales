package com.example.sales_management.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.sales_management.Models.Order;
import com.example.sales_management.Services.OrderService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {

    OrderService orderService;

    // Show list of all orders
    @GetMapping("/list")
    public String getOrders(Model model) {
        model.addAttribute("orderList", orderService.findAll());
        return "orders";  // Ensure this matches the name of your HTML file
    }

    // Add a new order
    @PostMapping("/add")
    public String addOrder(@ModelAttribute Order order) {
        orderService.save(order);
        return "redirect:/order/list";
    }

    // Show edit form for an order
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Order order = orderService.findById(id);
        if (order != null) {
            model.addAttribute("order", order);
            return "edit-order";  // Ensure this matches the name of your edit HTML file
        } else {
            // Handle case when order is not found
            model.addAttribute("error", "Order not found");
            return "redirect:/order/list";
        }
    }

    // Process editing an order
    @PostMapping("/edit")
    public String editOrder(@ModelAttribute Order order) {
        orderService.update(order.getOrderID(), order);  // Update the order using the ID
        return "redirect:/order/list";
    }

    // Delete an order
    @PostMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteById(id);
        return "redirect:/order/list";
    }
}