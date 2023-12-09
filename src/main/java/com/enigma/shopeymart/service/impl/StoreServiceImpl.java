package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.dto.request.StoreRequest;
import com.enigma.shopeymart.dto.response.StoreResponse;
import com.enigma.shopeymart.entity.Store;
import com.enigma.shopeymart.repository.StoreRepository;
import com.enigma.shopeymart.service.StoreService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
//    CONTOH PENGGUNAAN ENTITY
    @Override
    public Store create(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public Store update(Store store) {
        Store currentStoreId = getById(store.getId());
        if (currentStoreId != null){
            return storeRepository.save(store);
        }
        return null;
    }

    @Override
    public void delete(String id) {
        storeRepository.deleteById(id);
    }

    @Override
    public List<Store> getAll() {
        return storeRepository.findAll();
    }

    @Override
    public Store getById(String id) {
        return storeRepository.findById(id).orElse(null);
    }

//    CONTOH PENGGUNAAN DTO


    @Override
    public StoreResponse create(StoreRequest storeRequest) {
        Store store = Store.builder()
                .name(storeRequest.getName())
                .noSiup(storeRequest.getNoSiup())
                .address(storeRequest.getAddress())
                .mobilePhone(storeRequest.getMobilePhone())
                .build();
        storeRepository.save(store);
        return StoreResponse.builder()
                .storeName(store.getName())
                .address(store.getAddress())
                .noSiup(store.getNoSiup())
                .phone(store.getMobilePhone())
                .build();
    }
}
