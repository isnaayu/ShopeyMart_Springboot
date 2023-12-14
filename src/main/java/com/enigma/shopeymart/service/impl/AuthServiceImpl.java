package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.constant.ERole;
import com.enigma.shopeymart.dto.request.AuthRequest;
import com.enigma.shopeymart.dto.response.LoginResponse;
import com.enigma.shopeymart.dto.response.RegisterResponse;
import com.enigma.shopeymart.entity.*;
import com.enigma.shopeymart.repository.UserCredentialRepository;
import com.enigma.shopeymart.security.JwtUtil;
import com.enigma.shopeymart.service.AdminService;
import com.enigma.shopeymart.service.AuthService;
import com.enigma.shopeymart.service.CustomerService;
import com.enigma.shopeymart.service.RoleService;
import com.enigma.shopeymart.util.ValidationUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private final AdminService adminService;
    private final RoleService roleService;
    private final JwtUtil jwtUtil;
    private final ValidationUtil validationUtil;
    private final AuthenticationManager authenticationManager;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public RegisterResponse registerCustomer(AuthRequest authRequest) {
        try {
            validationUtil.validate(authRequest);
            //    TODO 2 : set Role
            Role role = Role.builder()
                    .name(ERole.ROLE_CUSTOMER)
                    .build();
            role = roleService.getOrSave(role);
//            TODO 1 : set Credential
            UserCredential userCredential = UserCredential.builder()
                    .username(authRequest.getUsername())
                    .password(passwordEncoder.encode(authRequest.getPassword()))
                    .role(role)
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);


//            TODO 3 : Set Customer

            Customer customer = Customer.builder()
                    .userCredential(userCredential)
                    .name(authRequest.getName())
                    .address(authRequest.getAddress())
                    .mobilePhone(authRequest.getMobilePhone())
                    .email(authRequest.getEmail())
                    .build();
            customerService.createNewCustomer(customer);
            return RegisterResponse.builder()
                    .username(userCredential.getUsername())
                    .role(userCredential.getRole().getName().toString())
                    .build();
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exist");
        }
    }

    @Override
    public LoginResponse login(AuthRequest authRequest) {
//        Tempat untuk logic login
        validationUtil.validate(authRequest);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getUsername().toLowerCase(), authRequest.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

//        Object AppUser
        AppUser appUser = (AppUser) authentication.getPrincipal();
        String token = jwtUtil.generateToken(appUser);
        return LoginResponse.builder()
                .token(token)
                .role(appUser.getRole().name())
                .build();
    }

    @Override
    public RegisterResponse registerAdmin(AuthRequest authRequest) {
        try {
            validationUtil.validate(authRequest);
            //    TODO 2 : set Role
            Role role = Role.builder()
                    .name(ERole.ROLE_ADMIN)
                    .build();
            role = roleService.getOrSave(role);
//            TODO 1 : set Credential
            UserCredential userCredential = UserCredential.builder()
                    .username(authRequest.getUsername())
                    .password(passwordEncoder.encode(authRequest.getPassword()))
                    .role(role)
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);


//            TODO 3 : Set Admin

            Admin admin = Admin.builder()
                    .userCredential(userCredential)
                    .name(authRequest.getName())
                    .phoneNumber(authRequest.getMobilePhone())
                    .build();
            adminService.createNewAdmin(admin);
            return RegisterResponse.builder()
                    .username(userCredential.getUsername())
                    .role(userCredential.getRole().getName().toString())
                    .build();
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exist");
        }
    }
}
