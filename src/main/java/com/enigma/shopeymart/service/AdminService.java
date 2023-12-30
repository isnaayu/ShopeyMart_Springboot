package com.enigma.shopeymart.service;

import com.enigma.shopeymart.dto.response.AdminResponse;
import com.enigma.shopeymart.dto.response.CustomerResponse;
import com.enigma.shopeymart.entity.Admin;
import com.enigma.shopeymart.entity.Customer;

public interface AdminService {
    AdminResponse createNewAdmin(Admin request);
}
