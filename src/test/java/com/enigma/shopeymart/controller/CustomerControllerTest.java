package com.enigma.shopeymart.controller;

import com.enigma.shopeymart.dto.request.CustomerRequest;
import com.enigma.shopeymart.dto.response.CustomerResponse;
import com.enigma.shopeymart.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @InjectMocks //
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @BeforeEach
    public void setUp(){
//        inisialisasi mock dan controller
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void create() {
        CustomerRequest customerRequest = new CustomerRequest();
        CustomerResponse customerResponse = new CustomerResponse();

        when(customerService.create(customerRequest)).thenReturn(customerResponse);

        CustomerResponse actualResponse = customerController.create(customerRequest);

        assertEquals(customerResponse, actualResponse);
    }

    @Test
    void getAll() {
        List<CustomerResponse> expectedResponse = new ArrayList<>();
        when(customerService.getAll()).thenReturn(expectedResponse);

        List<CustomerResponse> actualResponse = customerController.getAll();
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getById() {
        String customerId = "1";
        CustomerResponse expectedResponse = new CustomerResponse();

        when(customerService.getById(customerId)).thenReturn(expectedResponse);
        CustomerResponse actualResponse = customerController.getById(customerId);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void update() {
        CustomerResponse expectedResponse = new CustomerResponse();
        CustomerRequest customerRequest = new CustomerRequest();

        when(customerService.update(customerRequest)).thenReturn(expectedResponse);
        CustomerResponse actualResponse = customerController.update(customerRequest);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void delete() {
        String customerId = "1";
        customerController.delete(customerId);
        verify(customerService, times(1)).delete(customerId);

    }
}