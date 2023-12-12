package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.dto.request.OrderRequest;
import com.enigma.shopeymart.dto.response.*;
import com.enigma.shopeymart.entity.*;
import com.enigma.shopeymart.repository.OrderRepository;
import com.enigma.shopeymart.service.CustomerService;
import com.enigma.shopeymart.service.OrderService;
import com.enigma.shopeymart.service.ProductPriceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final ProductPriceService productPriceService;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public OrderResponse create(OrderRequest orderRequest) {
//        TODO 1 : validate Customer
        CustomerResponse customerResponse = customerService.getById(orderRequest.getCustomerId());
//        TODO 2 : Convert orderDetailRequest to orderDetail
        List<OrderDetail> orderDetails = orderRequest.getOrderDetails().stream().map(orderDetailRequest -> {
//            TODO 3: Validate Product Price
            ProductPrice productPrice = productPriceService.getById(orderDetailRequest.getProductPriceId());
            return OrderDetail.builder()
                    .productPrice(productPrice)
                    .quantity(orderDetailRequest.getQuantity())
                    .build();
        }).toList();
        //TODO 4 : Create New Order
        Order order = Order.builder()
                .customer(Customer.builder()
                        .id(customerResponse.getId())
                        .build())
                .transDate(LocalDateTime.now())
                .orderDetails(orderDetails)
                .build();
        orderRepository.saveAndFlush(order);

        List<OrderDetailResponse> orderDetailResponses = order.getOrderDetails().stream().map(orderDetail -> {
//            TODO 5 : Set order from orderDetail after creating new order
            orderDetail.setOrder(order);
            System.out.println(order);
//            TODO 6 : Change the stock from the purchase quantity
            ProductPrice currentProductPrice = orderDetail.getProductPrice();
            currentProductPrice.setStock(currentProductPrice.getStock() - orderDetail.getQuantity());
            return OrderDetailResponse.builder()
                    .orderDetailsId(orderDetail.getId())
                    .quantity(orderDetail.getQuantity())
//                    TODO 7 : Convert Product  to ProductResponse(ProductPrice)
                    .product(ProductResponse.builder()
                            .ProductId(currentProductPrice.getId())
                            .ProductName(currentProductPrice.getProduct().getName())
                            .description(currentProductPrice.getProduct().getDescription())
                            .stock(currentProductPrice.getStock())
                            .price(currentProductPrice.getPrice())
//                            TODO 8: Convert Store to StoreResponse(productPrice)
                            .store(StoreResponse.builder()
                                    .id(currentProductPrice.getId())
                                    .storeName(currentProductPrice.getStore().getName())
                                    .noSiup(currentProductPrice.getStore().getNoSiup())
                                    .address(currentProductPrice.getStore().getAddress())
                                    .phone(currentProductPrice.getStore().getMobilePhone())
                                    .build())
                            .build())
                    .build();
        }).toList();
//        TODO 9 : Convert Customer to Customer Response
//        Customer customer = Customer.builder()
//                .id(customerResponse.getId())
//                .name(customerResponse.getCustomerName())
//                .email(customerResponse.getEmail())
//                .address(customerResponse.getCustomerAddress())
//                .mobilePhone(customerResponse.getPhone())
//                .build();

//        TODO 10 : Return OrderResponse
        return OrderResponse.builder()
                .orderId(order.getId())
                .customer(customerResponse)
                .orderDetails(orderDetailResponses)
                .transDate(order.getTransDate())
                .build();
//        return orderResponse;
    }

    @Override
    public OrderResponse getById(String id) {
        return null;
    }

    @Override
    public List<OrderResponse> getAllOrder() {
        return null;
    }
}