package com.example.sales_management.Controllers;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.sales_management.Models.User;
import com.example.sales_management.Services.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;
    PasswordEncoder passwordEncoder;

    @GetMapping("/list")
    public String getAllUsers(Model model) {
       List<User> users = userService.findAll();
       model.addAttribute("list", users);
       model.addAttribute("user", new User());
        return "users";
    }
//    @GetMapping("/get")
//    public User getUser(@RequestParam String userID) {
//        return userService.findById(userID);
//    }
//    @GetMapping("/getmyinfo")
//    public User getMyInfo() {
//        return userService.getMyInfo();
//    }
    @PostMapping ("/add")
    public String addUser(@ModelAttribute User user) {
        if(userService.findById(user.getUserID())==null){
            user.setPassWord(passwordEncoder.encode(user.getPassWord()));
        }
        userService.save(user);
        return "redirect:/user/list";
    }
    
    @PostMapping ("/delete/{id}")
    public String deleteUser(@PathVariable  String id) {
        userService.deleteById(id);
        return "redirect:/user/list";
    }


}