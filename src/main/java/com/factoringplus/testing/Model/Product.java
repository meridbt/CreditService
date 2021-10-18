package com.factoringplus.testing.Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.factoringplus.testing.DTO.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = true)
    private Date created;

    @Column(nullable = true)
    private Date updated;

    @Column(nullable = false)
    private Boolean enabled;

    @Column(nullable = false)
    private Double rate;

    @Column(nullable = true)
    private Long monthsLimit;

    @Column(nullable = true)
    private Long sumLimit;

    public Product(Double rate, Long monthsLimit, Long sumLimit) {
        this.rate = rate;
        this.monthsLimit = monthsLimit;
        this.sumLimit = sumLimit;
        this.enabled = true;
        this.created = new Date();
        this.updated = new Date();
    }

    public Product(ProductDTO dto) {
        this.id = dto.getId();
        this.rate = dto.getRate();
        this.monthsLimit = dto.getMonthsLimit();
        this.sumLimit = dto.getSumLimit();
        if (dto.getId().equals(0l)) {
            this.enabled = true;
            this.created = new Date();
        }
        this.updated = new Date();
    }

    public ProductDTO toDto() {
        return new ProductDTO(
            this.id,
            this.rate,
            this.monthsLimit,
            this.sumLimit
        );
    }
    
}
