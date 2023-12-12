package com.enigma.shopeymart.service;

import com.enigma.shopeymart.dto.request.ProductRequest;
import com.enigma.shopeymart.dto.response.ProductResponse;
import com.enigma.shopeymart.entity.Product;
import com.enigma.shopeymart.entity.ProductPrice;

import java.util.List;

public interface ProductService {
    ProductResponse create(ProductRequest productRequest);
//    List<ProductResponse> getAll();
    ProductResponse update(ProductRequest productRequest);
    ProductResponse getById(String id);
    void delete(String id);

    ProductResponse createProductAndProductPrice(ProductRequest productRequest);
    List<Product> getAll();
}
