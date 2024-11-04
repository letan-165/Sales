package com.example.sales_management.Controllers;


import com.example.sales_management.Services.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import com.example.sales_management.Services.UserService;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class AuthenticationControler {

    UserService userService ;   
    AuthenticationService authenticationService ;

    @GetMapping("/login")
    String login(@RequestParam String username, @RequestParam String password) {
        return authenticationService.authenticate(username,password);
    }

    @GetMapping("/introspect")
    boolean introspect (@RequestParam String token) throws JOSEException, ParseException {
        return authenticationService.introspect(token);
    }
    
}
