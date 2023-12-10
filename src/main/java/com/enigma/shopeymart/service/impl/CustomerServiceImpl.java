package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.dto.request.CustomerRequest;
import com.enigma.shopeymart.dto.response.CustomerResponse;
import com.enigma.shopeymart.entity.Customer;
import com.enigma.shopeymart.repository.CustomerRepository;
import com.enigma.shopeymart.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponse create(CustomerRequest customerRequest) {
        Customer customer = Customer.builder()
                .name(customerRequest.getName())
                .address(customerRequest.getAddress())
                .email(customerRequest.getEmail())
                .mobilePhone(customerRequest.getMobilePhone())
                .build();
        customerRepository.save(customer);
        return CustomerResponse.builder()
                .id(customer.getId())
                .CustomerName(customer.getName())
                .CustomerAddress(customer.getAddress())
                .email(customer.getEmail())
                .Phone(customer.getMobilePhone())
                .build();
    }

    @Override
    public List<CustomerResponse> getAll() {
        return customerRepository.findAll().stream().map((user ->{
            CustomerResponse customerResponse = new CustomerResponse();
            customerResponse.setId(user.getId());
            customerResponse.setCustomerName(user.getName());
            customerResponse.setCustomerAddress(user.getAddress());
            customerResponse.setEmail(user.getEmail());
            customerResponse.setPhone(user.getMobilePhone());
            return customerResponse;
        })).collect(Collectors.toList());
    }

    @Override
    public CustomerResponse getById(String id) {
        return customerRepository.findById(id).map((user ->{
            CustomerResponse customerResponse = new CustomerResponse();
            customerResponse.setId(user.getId());
            customerResponse.setCustomerName(user.getName());
            customerResponse.setCustomerAddress(user.getAddress());
            customerResponse.setEmail(user.getEmail());
            customerResponse.setPhone(user.getMobilePhone());
            return customerResponse;
        })).orElse(null);
//        return customerResponse;
//        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(String id) {
        if (getById(id) != null){
            customerRepository.deleteById(id);
            System.out.println("Delete Succesfully");
        }else {
            System.out.printf("Delete Failed!");
        }
    }

    @Override
    public CustomerResponse update(CustomerRequest customerRequest) {
        CustomerResponse currentCustomer = getById(customerRequest.getId());
        if (currentCustomer != null){
            Customer customer = Customer.builder()
                    .id(customerRequest.getId())
                    .name(customerRequest.getName())
                    .address(customerRequest.getAddress())
                    .email(customerRequest.getEmail())
                    .mobilePhone(customerRequest.getMobilePhone())
                    .build();
            customerRepository.save(customer);
            return CustomerResponse.builder()
                    .id(customer.getId())
                    .CustomerName(customer.getName())
                    .CustomerAddress(customer.getAddress())
                    .email(customer.getEmail())
                    .Phone(customer.getMobilePhone())
                    .build();
        }
        return null;
    }
}
