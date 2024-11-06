package com.example.sales_management.Controllers;


import java.text.ParseException;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sales_management.Services.AuthenticationService;
import com.example.sales_management.Services.UserService;
import com.nimbusds.jose.JOSEException;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class AuthenticationControler {

    UserService userService ;   
    AuthenticationService authenticationService ;

    
    @GetMapping("/")
    public String index() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,HttpServletResponse response) {        
        String token = authenticationService.authenticate(username, password);
        if(!"TokenFalse".equals(token)){
            ResponseCookie cookie = ResponseCookie.from("Authorization",token)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .build();
            response.addHeader("Set-Cookie", cookie.toString());
            return "list";
        }
        return "login";
    }

    @GetMapping("/introspect")
    public boolean introspect (@RequestParam String token) throws JOSEException, ParseException {
        return authenticationService.introspect(token);
    }
    
}
