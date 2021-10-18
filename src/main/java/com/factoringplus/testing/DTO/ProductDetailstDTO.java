package com.factoringplus.testing.DTO;

import java.util.List;

import com.factoringplus.testing.Model.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailstDTO {

    private Long id;

    private Double rate;

    private Long monthsLimit;

    private Long sumLimit;

    private List<RuleDTO> rules;

    public ProductDetailstDTO(Product product, List<RuleDTO> rules) {
        this.id = product.getId();
        this.rate = product.getRate();
        this.monthsLimit = product.getMonthsLimit();
        this.sumLimit = product.getSumLimit();
        this.rules = rules;
    }
    
}
