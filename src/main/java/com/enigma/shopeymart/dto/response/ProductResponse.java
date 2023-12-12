package com.enigma.shopeymart.dto.response;

import com.enigma.shopeymart.entity.ProductPrice;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ProductResponse {
    private String ProductId;
    private String ProductName;
    private String description;
    private Long price;
    private Integer stock;
    private StoreResponse store;
//    private List<ProductPrice> productPriceList;
}
