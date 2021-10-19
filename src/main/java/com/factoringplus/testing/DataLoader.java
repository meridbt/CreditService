package com.factoringplus.testing;

import com.factoringplus.testing.Model.Product;
import com.factoringplus.testing.Model.Rule;
import com.factoringplus.testing.Model.Rule.Filter;
import com.factoringplus.testing.Repository.ProductRepository;
import com.factoringplus.testing.Repository.RuleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired 
    ProductRepository productRepository;

    @Autowired
    RuleRepository ruleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (productRepository.count() == 0) {
            var p = productRepository.save(new Product(6d, 36l, 200000l));
            ruleRepository.save(new Rule(Filter.SALARY_NOT_LESS, 50000d, p));
            ruleRepository.save(new Rule(Filter.IS_DEBTOR, 0d, p));
            p = productRepository.save(new Product(15d, null, null));
            ruleRepository.save(new Rule(Filter.IS_DEBTOR, 0d, p));
            p = productRepository.save(new Product(12d, 60l, 1000000l));            
            ruleRepository.save(new Rule(Filter.SALARY_NOT_LESS, 25000d, p));
        }
    }
    
}
