package com.enigma.shopeymart.controller;

import com.enigma.shopeymart.constant.AppPath;
import com.enigma.shopeymart.dto.request.StoreRequest;
import com.enigma.shopeymart.dto.response.StoreResponse;
import com.enigma.shopeymart.entity.Store;
import com.enigma.shopeymart.service.impl.StoreServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.STORE) //ini digunakan untuk default alamat jadi tidak harus menggunakaan /store
public class StoreController {
    private final StoreServiceImpl storeService;

    @PostMapping //http://localhost:8080/store //POST
    public Store createStore(@RequestBody Store store){
        return storeService.create(store);
    }

    @GetMapping //http://localhost:8080/store //GET
    public List<Store> getAllStore(){
        return storeService.getAll();
    }
    @GetMapping("/{id}") //http://localhost:8080/store/7eed61a6-7dab-4a5d-9406-0506934ffcd5 //GET
    public Store getById(@PathVariable String id){
        return storeService.getById(id);
    }

    @PutMapping//http://localhost:8080/store/b1df279a-feed-499e-92fc-0e710de6fca3 //PUT
    public Store updateStore(@RequestBody Store store){
        return storeService.update(store);
    }

    @DeleteMapping("/store/{id}") //http://localhost:8080/store
    public void deleteStore(@PathVariable String id){
        storeService.delete(id);
    }

    @PostMapping("/v1")
    public StoreResponse createStores(@RequestBody StoreRequest storeRequest){
        return storeService.create(storeRequest);
    }


}
