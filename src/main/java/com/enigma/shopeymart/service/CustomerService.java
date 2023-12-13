package com.enigma.shopeymart.service;

import com.enigma.shopeymart.dto.request.CustomerRequest;
import com.enigma.shopeymart.dto.response.CustomerResponse;
import com.enigma.shopeymart.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CustomerService {
    CustomerResponse create(CustomerRequest customerRequest);
    CustomerResponse createNewCustomer(Customer request);
    List<CustomerResponse> getAll();
    CustomerResponse getById(String id);
    void delete(String id);
    CustomerResponse update(CustomerRequest customerRequest);

}
