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
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
            System.out.println("Delete Successfully");
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

    @Override
    public Page<ProductResponse> getAllByNameOrPrice(String name, Long maxPrice, Integer page, Integer size) {
//        Specification untuk menentukan kriteria, disini criteria pencarian ditandakan dengan root, root yang dimaksus adalah entity model
        Specification<Product> specification = ((root, query, criteriaBuilder) -> {
//            Join digunakan untuk merelasikan antara product dan product peice
            Join<Product, ProductPrice> productPrices = root.join("productPrices");
//            Predicate digunakan untuk menggunakan LIKE dimana nanti kita akan menggunakan kondisi pencarian parameter
//            disini kita akan mencari nama product atau harga yang sama atau harga dibawahnya, makanya menggunakan lessThanOrEquals
            List<Predicate> predicates = new ArrayList<>();
            if (name != null) predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            if (maxPrice != null) predicates.add(criteriaBuilder.lessThanOrEqualTo(productPrices.get("price"), maxPrice));
//            kode return mengembalikan query dimana pada dasarnya kita membangun klause where yang sudah ditentukan dari predicate atau kriteria
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        });
        Pageable pageable = PageRequest.of(page,size);
        Page<Product> products = productRepository.findAll(specification, pageable);
//        Ini digunakan untuk menyimpan rensponse product yang baru
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products.getContent()){
//            for disini digunakan untuk mengiterasi daftar product yang disimpan dalam object
            Optional<ProductPrice> productPrice = product.getProductPrices()
                    .stream()
                    .filter(ProductPrice::isActive).findFirst();
            if (productPrice.isEmpty()) continue; //kindisi ini digunakan untuk memeriksa apakah productPricenya kosong atau tidak, kalau tidak maka di skip
            Store store = productPrice.get().getStore(); //ini digunakan untuk jika harga yang aktif ditenttukan di store
            productResponses.add(toProductResponse(store, product, productPrice.get()));

        }
        return new PageImpl<>(productResponses, pageable, products.getTotalElements());
    }

    private static ProductResponse toProductResponse(Store store, Product product, ProductPrice productPrice){
        return ProductResponse.builder()
                .ProductId(product.getId())
                .ProductName(product.getName())
                .description(product.getDescription())
                .price(productPrice.getPrice())
                .stock(productPrice.getStock())
                .store(StoreResponse.builder()
                        .id(store.getId())
                        .storeName(store.getName())
                        .noSiup(store.getNoSiup())
                        .address(store.getAddress())
                        .phone(store.getMobilePhone())
                        .build())
                .build();
    }
}
