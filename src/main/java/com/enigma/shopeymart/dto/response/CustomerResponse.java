package com.enigma.shopeymart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {
    private String id;
    private String CustomerName;
    private String CustomerAddress;
    private String Phone;
    private String email;
}
