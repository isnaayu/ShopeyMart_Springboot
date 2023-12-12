package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.dto.request.ProductRequest;
import com.enigma.shopeymart.dto.response.ProductResponse;
import com.enigma.shopeymart.dto.response.StoreResponse;
import com.enigma.shopeymart.entity.Product;
import com.enigma.shopeymart.entity.ProductPrice;
import com.enigma.shopeymart.entity.Store;
import com.enigma.shopeymart.repository.ProductRepository;
import com.enigma.shopeymart.service.ProductPriceService;
import com.enigma.shopeymart.service.ProductService;
import com.enigma.shopeymart.service.StoreService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductPriceService productPriceService;
    private final StoreService storeService;
    @Override
    public ProductResponse create(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .build();
        productRepository.save(product);
        return ProductResponse.builder()
                .ProductId(product.getId())
                .ProductName(product.getName())
                .description(product.getDescription())
                .build();
    }

//    @Override
//    public List<ProductResponse> getAll() {
//        return productRepository.findAll().stream().map(product -> ProductResponse.builder()
//                    .ProductId(product.getId())
//                    .ProductName(product.getName())
//                    .description(product.getDescription())
//                    .productPriceList(product.getProductPrices())
//                    .build()
//        ).collect(Collectors.toList());
//    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll().stream().map(product -> Product.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .productPrices(product.getProductPrices())
                .build()
        ).collect(Collectors.toList());
    }

    @Override
    public ProductResponse update(ProductRequest productRequest) {
        ProductResponse currentProduct = getById(productRequest.getProductId());
        if (currentProduct != null){
            Product product = Product.builder()
                    .id(productRequest.getProductId())
                    .name(productRequest.getName())
                    .description(productRequest.getDescription())
                    .build();
            productRepository.save(product);
            return ProductResponse.builder()
                    .ProductId(product.getId())
                    .ProductName(product.getName())
                    .description(product.getDescription())
                    .build();
        }
        return null;
    }

    @Override
    public ProductResponse getById(String id) {
        return productRepository.findById(id).map((product ->{
            ProductResponse productResponse = new ProductResponse();
            productResponse.setProductId(product.getId());
            productResponse.setProductName(product.getName());
            productResponse.setDescription(product.getDescription());
            return productResponse;
        })).orElse(null);
    }

    @Override
    public void delete(String id) {
        if (getById(id) != null){
            productRepository.deleteById(id);
            System.out.println("Delete Succesfully");
        }else {
            System.out.printf("Delete Failed!");
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ProductResponse createProductAndProductPrice(ProductRequest productRequest) {
        Store store = storeService.getById(productRequest.getStoreId());
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .build();
        productRepository.saveAndFlush(product);
        ProductPrice productPrice = ProductPrice.builder()
                .price(productRequest.getPrice())
                .stock(productRequest.getStock())
                .isActive(true)
                .store(store)
                .product(product)
                .build();
        productPriceService.create(productPrice);
        return ProductResponse.builder()
                .ProductId(product.getId())
                .ProductName(product.getName())
                .description(product.getDescription())
                .price(productPrice.getPrice())
                .stock(productPrice.getStock())
                .store(StoreResponse.builder()
                        .id(store.getId())
                        .noSiup(store.getNoSiup())
                        .storeName(store.getName())
                        .build())
                .build();
    }
}
