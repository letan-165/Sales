package com.example.sales_management.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.sales_management.Models.User;
import com.example.sales_management.Services.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    //Hiển thị danh sách
    @GetMapping("/")
    public String getAllUsers(Model model) {

        model.addAttribute("users", userService.findAll());
        return "list";
    }
    //Thêm người dùng
    @GetMapping("/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "add";
    }
    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/";
    }
    //Sửa người dùng 
    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable String id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "edit";
    }
    @PostMapping("/edit")
    public String updateUser(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/";
    }
    //Xóa người dùng
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable String id) {
        userService.deleteById(id);
        return "redirect:/";
    }
    



    

}