package com.enigma.shopeymart.controller;

import com.enigma.shopeymart.constant.AppPath;
import com.enigma.shopeymart.dto.request.AuthRequest;
import com.enigma.shopeymart.dto.request.OrderRequest;
import com.enigma.shopeymart.dto.response.CommonResponse;
import com.enigma.shopeymart.dto.response.LoginResponse;
import com.enigma.shopeymart.dto.response.OrderResponse;
import com.enigma.shopeymart.dto.response.RegisterResponse;
import com.enigma.shopeymart.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    }

    @PostMapping("/register/admin")
    public RegisterResponse createAdmin(@RequestBody AuthRequest authRequest){
        return authService.registerAdmin(authRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest){
        LoginResponse loginResponse = authService.login(authRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .data(loginResponse)
                        .message("Succesfully Login")
                        .statusCode(HttpStatus.CREATED.value())
                        .build());
    }
}
