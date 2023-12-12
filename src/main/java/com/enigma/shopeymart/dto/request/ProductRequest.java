package com.enigma.shopeymart.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProductRequest {
    private String ProductId;

    @NotBlank(message = "Product Name is Required")
    private String name;

    @NotBlank(message = "Description is Required")
    private String description;

    @NotBlank(message = "Price is Required")
    @Min(value = 0, message = "Price must be greater than equal 0")
    private Long price;

    @NotBlank(message = "Stock is Required")
    @Min(value = 0, message = "Stock must be greater than equal 0")
    private Integer stock;

    @NotBlank(message = "StoreId is Required")
    private String storeId;
}
