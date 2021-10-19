package com.factoringplus.testing;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import com.factoringplus.testing.Controller.ProductsController;
import com.factoringplus.testing.DTO.ClaimDTO;
import com.factoringplus.testing.Model.Product;
import com.factoringplus.testing.Model.Rule;
import com.factoringplus.testing.Model.Rule.Filter;
import com.factoringplus.testing.Repository.ProductRepository;
import com.factoringplus.testing.Repository.RuleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(ProductsController.class)
public class ProductsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    RuleRepository ruleRepository;

    @Test
    public void applyClaim_success() throws Exception {
        var p1 = new Product(6d, 36l, 200000l);
        var p2 = new Product(15d, null, null);
        var p3 = new Product(12d, 60l, 1000000l);

        var rules = Arrays.asList(
            new Rule(Filter.SALARY_NOT_LESS, 50000d, p1),
            new Rule(Filter.IS_DEBTOR, 0d, p1),
            new Rule(Filter.IS_DEBTOR, 0d, p2),
            new Rule(Filter.SALARY_NOT_LESS, 25000d, p3)
        );

        var claim1 = ClaimDTO.builder().salary(30000d).claim(20000d).isDebtor(true).build();
        var claim2 = ClaimDTO.builder().salary(60000d).claim(300000d).isDebtor(false).build();

        Mockito.when(ruleRepository.findAllByEnabled(true)).thenReturn(rules);

        mockMvc.perform(MockMvcRequestBuilders
        .post("/products/apply")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsBytes(claim1)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].rate", is(12.0)));

        mockMvc.perform(MockMvcRequestBuilders
        .post("/products/apply")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsBytes(claim2)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)));
    }
    
}
