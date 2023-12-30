package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.dto.request.ProductRequest;
import com.enigma.shopeymart.dto.response.ProductResponse;
import com.enigma.shopeymart.entity.Product;
import com.enigma.shopeymart.entity.ProductPrice;
import com.enigma.shopeymart.entity.Store;
import com.enigma.shopeymart.repository.ProductRepository;
import com.enigma.shopeymart.service.ProductPriceService;
import com.enigma.shopeymart.service.ProductService;
import com.enigma.shopeymart.service.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceImplTest {
    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final ProductPriceService productPriceService = mock(ProductPriceService.class);
    private final StoreService storeService = mock(StoreService.class);
    private final ProductService productService = new ProductServiceImpl(productRepository,productPriceService, storeService);

    @BeforeEach
    public void setUp(){
        reset(productRepository, storeService, productPriceService);
    }



    @Test
    void createProductAndProductPrice() {
//        TODO : SET DUMMY STORE
        Store dummyStore = new Store();
        dummyStore.setId("storeId");
        dummyStore.setName("abadi");
        dummyStore.setMobilePhone("032343");
        dummyStore.setAddress("ragunan");
        dummyStore.setNoSiup("1234");

        when(storeService.getById(anyString())).thenReturn(dummyStore);



//        TODO : Mock Product that will be save
        Product saveProduct = new Product();
        saveProduct.setId("productId");
        saveProduct.setName("Oreo");
        saveProduct.setDescription("enak");
        when(productRepository.saveAndFlush(any(Product.class))).thenReturn(saveProduct);

        //        TODO : Data Dummy Request
        ProductRequest dummyRequest = mock(ProductRequest.class);
        when(dummyRequest.getStoreId()).thenReturn(dummyStore.getId());
        when(dummyRequest.getName()).thenReturn(saveProduct.getName());
        when(dummyRequest.getPrice()).thenReturn(20000L);
        when(dummyRequest.getDescription()).thenReturn(saveProduct.getDescription());
        when(dummyRequest.getStock()).thenReturn(1);

//        TODO : CAll METHOD CREATE
        ProductResponse productResponse = productService.createProductAndProductPrice(dummyRequest);

//        TODO : VAlidate Response
        assertNotNull(productResponse);
        assertEquals(saveProduct.getName(), productResponse.getProductName());

//        TODO : VAlidate That the product price was set correct
        assertEquals(dummyRequest.getPrice(), productResponse.getPrice());
        assertEquals(dummyRequest.getStock(), productResponse.getStock());

//        TODO : Validate Interaction with Store
        assertEquals(dummyStore.getId(), productResponse.getStore().getId());

//        TODO : Validate
        verify(storeService).getById(anyString());
        verify(productRepository).saveAndFlush(any(Product.class));
        verify(productPriceService).create(any(ProductPrice.class));

    }
}