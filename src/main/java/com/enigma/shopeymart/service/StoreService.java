package com.enigma.shopeymart.service;

import com.enigma.shopeymart.dto.request.StoreRequest;
import com.enigma.shopeymart.dto.response.StoreResponse;
import com.enigma.shopeymart.entity.Store;

import java.util.List;

public interface StoreService{
    Store create(Store store);
    Store update(Store store);
    void delete(String id);
    List<Store> getAll();
    Store getById(String id);

    StoreResponse create(StoreRequest storeRequest);
}
