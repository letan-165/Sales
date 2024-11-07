package com.example.sales_management.Controllers;


import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sales_management.Services.AuthenticationService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class AuthenticationControler {
    AuthenticationService authenticationService ;

    @GetMapping("/")
    public String index(HttpSession session) {
        session.invalidate();
        return "login";
    }

    @PostMapping("/index")
    public String login(@RequestParam String username, @RequestParam String password,HttpServletResponse response,HttpSession session) {        
        String token = authenticationService.authenticate(username, password);
        session.setAttribute("username", username);
        if(!"TokenFalse".equals(token)){
            ResponseCookie cookie = ResponseCookie.from("Authorization",token)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .build();
            response.addHeader("Set-Cookie", cookie.toString());
            return "index";
        }
        return "redirect:/";
    }
}
