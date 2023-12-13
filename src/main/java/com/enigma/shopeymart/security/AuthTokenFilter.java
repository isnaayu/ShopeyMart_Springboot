package com.enigma.shopeymart.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthTokenFilter {
//    validasi token
//    autentikasi ke spring
    private final JwtUtil jwtUtil;

}
