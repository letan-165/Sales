package com.example.sales_management.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sales_management.Services.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/password-reset")
@RequiredArgsConstructor
public class PasswordResetController {
    private final UserService userService;

    @GetMapping
    public String showPasswordResetForm() {
        return "password-reset-form"; 
    }

    @PostMapping("/send-code")
    public String sendResetCode(@RequestParam("email") String email, Model model) {
        String resetCode = userService.sendResetCode(email);
        if (resetCode == null) {
            model.addAttribute("error", "Email không tồn tại.");
            return "password-reset-form"; 
        }
        model.addAttribute("email", email);
        return "verify-reset-code"; 
    }

    @PostMapping("/verify-code")
    public String verifyResetCode(@RequestParam("email") String email,
                                  @RequestParam("resetCode") String resetCode, Model model) {
        boolean isCodeValid = userService.verifyResetCode(email, resetCode);
        if (!isCodeValid) {
            model.addAttribute("error", "Mã xác thực không hợp lệ.");
            model.addAttribute("email", email);
            return "verify-reset-code"; 
        }
        model.addAttribute("email", email);
        return "new-password-form"; 
    }

    @PostMapping("/update-password")
    public String updatePassword(@RequestParam("email") String email,
                                 @RequestParam("newPassword") String newPassword, Model model) {
        boolean isUpdated = userService.updatePassword(email, newPassword);
        if (!isUpdated) {
            model.addAttribute("error", "Cập nhật mật khẩu không thành công.");
            return "new-password-form"; 
        }
        return "redirect:/"; 
    }
}
