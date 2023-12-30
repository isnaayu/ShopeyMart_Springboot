package com.enigma.shopeymart.controller;

import com.enigma.shopeymart.dto.request.ProductRequest;
import com.enigma.shopeymart.dto.response.CommonResponse;
import com.enigma.shopeymart.dto.response.ProductResponse;
import com.enigma.shopeymart.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ProductControllerTest {
    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void createProduct() {
//        TODO : DATA DUMMY
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("Oreo");
        productRequest.setPrice(100L);

//        TODO : DATA RESPONSE
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId("1");
        productResponse.setProductName("Oreo");
        productResponse.setPrice(100L);

        when(productService.createProductAndProductPrice(productRequest)).thenReturn(productResponse);

        ResponseEntity<?> responseEntity = productController.createProduct(productRequest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        CommonResponse<ProductResponse> commonResponse = (CommonResponse<ProductResponse>) responseEntity.getBody();
        assertEquals(HttpStatus.CREATED.value(), commonResponse.getStatusCode());
        assertEquals("Successfully Created new Product", commonResponse.getMessage());

    }
}