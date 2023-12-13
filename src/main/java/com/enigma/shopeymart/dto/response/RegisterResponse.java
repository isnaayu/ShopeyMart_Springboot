package com.enigma.shopeymart.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RegisterResponse {
    private String username;
    private String role;

}
