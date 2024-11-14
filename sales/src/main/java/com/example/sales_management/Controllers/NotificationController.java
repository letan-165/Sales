// package com.example.sales_management.Controllers;  

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;

// import com.example.sales_management.Models.Notification;  
// import com.example.sales_management.Services.NotificationService;  

// @Controller
// @RequestMapping("/notifications")
// public class NotificationController {

//     @Autowired
//     private NotificationService notificationService;

//     // Hiển thị danh sách thông báo
//     @GetMapping
//     public String showNotifications(Model model) {
//         List<Notification> notifications = notificationService.getAllNotifications();
//         model.addAttribute("notifications", notifications);
//         return "notification/notificationList";  // Tên view hiển thị danh sách thông báo
//     }

//     // Xóa thông báo
//     @PostMapping("/delete/{id}")
//     public String deleteNotification(@PathVariable("id") Long id) {
//         notificationService.deleteNotificationById(id);
//         return "redirect:/notifications";  // Sau khi xóa, điều hướng về trang danh sách thông báo
//     }

//     // Hiển thị thông báo chi tiết
//     @GetMapping("/{id}")
//     public String viewNotification(@PathVariable("id") Long id, Model model) {
//         Notification notification = notificationService.getNotificationById(id);
//         if (notification != null) {
//             model.addAttribute("notification", notification);
//             return "notification/notificationDetail";  // Tên view hiển thị chi tiết thông báo
//         } else {
//             return "redirect:/notifications";  // Nếu không tìm thấy thông báo, điều hướng lại về trang danh sách
//         }
//     }
// }
