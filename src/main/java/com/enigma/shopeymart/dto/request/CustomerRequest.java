package com.enigma.shopeymart.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    private String id;
    private String name;
    private String address;
    private String mobilePhone;
    private String email;
}
