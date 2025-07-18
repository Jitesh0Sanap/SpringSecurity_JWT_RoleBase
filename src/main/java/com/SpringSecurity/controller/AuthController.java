package com.SpringSecurity.controller;

import com.SpringSecurity.dto.JwtAuthRequest;
import com.SpringSecurity.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UsersService usersService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody JwtAuthRequest request){
        System.out.println("i am here");
        String verify = usersService.verify(request);
        return ResponseEntity.ok("token : => " +  verify);
    }
}
