package com.factoringplus.testing.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    private Double rate;

    private Long monthsLimit;

    private Long sumLimit;
    
}
