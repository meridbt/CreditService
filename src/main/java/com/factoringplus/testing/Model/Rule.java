package com.factoringplus.testing.Model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.factoringplus.testing.DTO.RuleDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "rules")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rule {

    public enum Filter{
        SALARY_NOT_LESS,
        IS_DEBTOR
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(nullable = true)
    private Date created;

    @Column(nullable = true)
    private Date updated;

    @Column(nullable = false)
    private Boolean enabled;

    @Column(nullable = false)
    private Filter filter;

    @Column(nullable = false)
    private Double value;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "product_id")
    private Product product;

    public Rule(Filter filter, Double value, Product product) {
        this.filter = filter;
        this.value = value;
        this.enabled = true;
        this.created = new Date();
        this.updated = new Date();
        this.product = product;
    }

    public Rule(RuleDTO dto, Product product) {
        this.filter = dto.getFilter();
        this.value = dto.getValue();
        this.enabled = true;
        this.created = new Date();
        this.updated = new Date();
        this.product = product;
    }

    public RuleDTO toDto() {
        return new RuleDTO(
            id,
            filter,
            value
        );
    }
    
}
