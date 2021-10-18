package com.factoringplus.testing.DTO;

import com.factoringplus.testing.Model.Rule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RuleDTO {

    private Long id;

    private Rule.Target target;

    private Rule.Relation relation;

    private Double value;
    
}
