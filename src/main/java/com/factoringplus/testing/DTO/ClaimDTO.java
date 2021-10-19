package com.factoringplus.testing.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClaimDTO {

    private Double salary;

    private Double claim;

    private Boolean isDebtor;
    
}
