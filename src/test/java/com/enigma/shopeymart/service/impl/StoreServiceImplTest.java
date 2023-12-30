package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.dto.request.StoreRequest;
import com.enigma.shopeymart.dto.response.StoreResponse;
import com.enigma.shopeymart.entity.Store;
import com.enigma.shopeymart.repository.StoreRepository;
import com.enigma.shopeymart.service.StoreService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
class StoreServiceImplTest {
    private final StoreRepository storeRepository = mock(StoreRepository.class);
    private final StoreService storeService = new StoreServiceImpl(storeRepository);

    @Test
    void itShouldReturnStoreWhenCreateNewStore() {
        StoreRequest dummyStoreRequest = new StoreRequest();
        dummyStoreRequest.setId("123");
        dummyStoreRequest.setAddress("ragunan");
        dummyStoreRequest.setName("jaya abadi");

//        Data db, request, response
        StoreResponse dummyStoreResponse = storeService.create(dummyStoreRequest);
//        Perform
        verify(storeRepository).save(any(Store.class));
//        Verify
        assertEquals(dummyStoreRequest.getName(), dummyStoreResponse.getStoreName());
//        assertEquals(dummyStoreRequest.getId(), dummyStoreResponse.getId());
    }

    @Test
    void itShouldGetAllDataSreWhenCallGetAll(){
        List<Store> dummyStore = new ArrayList<>();
        dummyStore.add(new Store("1", "123", "Jaya Abadi", "ragunan", "0543555"));
        dummyStore.add(new Store("2", "1234", "Jaya Abadiii", "ragunan2", "043345"));
        dummyStore.add(new Store("1", "12345", "Jaya Abadiii", "ragunan3", "035432"));

        when(storeRepository.findAll()).thenReturn(dummyStore);
        List<Store> retieveStore = storeService.getAll();
//        List<StoreResponse> retieveStore = storeService.getAll(); jika menggunakan storeResponse
        assertEquals(dummyStore.size(), retieveStore.size());

        for (int i = 0; i < dummyStore.size(); i++){
            assertEquals(dummyStore.get(i).getName(), retieveStore.get(i).getName());
        }
    }

    @Test
    void itShouldGetDataStoreOneWhenGetByIdStore(){
        String storeId = "1";
        Store store = new Store("1", "123", "Jaya Abadi", "ragunan", "0543555");
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));

        Store storeResult = storeService.getById(storeId);
        verify(storeRepository.findById(storeId));
        assertNotNull(storeResult);
        assertEquals(storeId, storeResult.getId());
        assertEquals("Jaya Abadi", storeResult.getName());
    }

    @Test
    void itShouldUpdateDataStoreWhenUpdateStore(){
//        String storeId = "1";

//        TODO : Data dummy for update
        Store dummyStore = new Store();
        dummyStore.setId("1");
        dummyStore.setName("jayaa");
        dummyStore.setAddress("ragunan");
        dummyStore.setNoSiup("123");
        dummyStore.setMobilePhone("944667");

//     TODO : Data exist
        Store storeExist = new Store("1", "123", "jaya", "ragunan", "944667");

//        when(storeRepository.findById(storeExist.getId())).thenReturn(Optional.ofNullable(dummyStore));
//        TODO : Conditions
        when(storeRepository.findById(storeExist.getId())).thenReturn(Optional.ofNullable(dummyStore));
        when(storeRepository.save(any())).thenReturn(dummyStore);

//      TODO : variabel penampung
        Store storeUpdate = storeService.update(dummyStore);
//        TODO : Verify conditions
        verify(storeRepository, times(1)).findById(dummyStore.getId());
        verify(storeRepository).save(dummyStore);
//        verify(storeRepository, times(1)).save(dummyStore);

//        TODO : Matcher
        assertNotNull(storeUpdate);
//        assertEquals(dummyStore.getId(), storeExist.getId());
        assertEquals(dummyStore.getName(), storeUpdate.getName());


//        Store resultUpdate = storeService.update(new Store("1", "123", "Jaya nusantara", "ragunan", "0543555"));
//        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));
//
//        verify(storeRepository.getById(store.getId()));
//        assertEquals(storeId, resultUpdate.getId());
//        assertEquals(store.getName(), resultUpdate.getName());

    }

    @Test
    void  itShouldDeleteDataByIdWhenDeleteStoreById(){
        String id = "1";
        storeService.delete(id);
        verify(storeRepository, times(1)).deleteById(id);
    }
}