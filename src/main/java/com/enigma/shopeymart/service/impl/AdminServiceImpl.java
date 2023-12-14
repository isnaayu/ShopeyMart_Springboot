package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.dto.response.AdminResponse;
import com.enigma.shopeymart.dto.response.CustomerResponse;
import com.enigma.shopeymart.entity.Admin;
import com.enigma.shopeymart.entity.Customer;
import com.enigma.shopeymart.repository.AdminRepository;
import com.enigma.shopeymart.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    @Override
    public AdminResponse createNewAdmin(Admin request) {
        Admin admin = adminRepository.saveAndFlush(request);
        return AdminResponse.builder()
                .id(admin.getId())
                .adminName(admin.getName())
                .Phone(admin.getPhoneNumber())
                .build();
    }
}
