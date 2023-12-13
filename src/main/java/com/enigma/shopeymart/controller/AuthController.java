package com.enigma.shopeymart.controller;

import com.enigma.shopeymart.constant.AppPath;
import com.enigma.shopeymart.dto.request.AuthRequest;
import com.enigma.shopeymart.dto.response.RegisterResponse;
import com.enigma.shopeymart.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.AUTH)
public class AuthController {
    private final AuthService authService;
    @PostMapping("/register")
    public RegisterResponse create(@RequestBody AuthRequest authRequest){
        return authService.registerCustomer(authRequest);
//        return null;
    }
}
