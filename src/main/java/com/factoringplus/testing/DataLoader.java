package com.factoringplus.testing;

import com.factoringplus.testing.Model.Product;
import com.factoringplus.testing.Model.Rule;
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
            var p = productRepository.save(new Product(5d, 36l, 200000l));
            ruleRepository.save(new Rule(Rule.Target.SALARY, Rule.Relation.GREATER_OR_EQUAL, 50000d, p));
            ruleRepository.save(new Rule(Rule.Target.DEBTOR, Rule.Relation.EQUAL, 0d, p));
            p = productRepository.save(new Product(15d, null, null));
            ruleRepository.save(new Rule(Rule.Target.DEBTOR, Rule.Relation.EQUAL, 0d, p));
            p = productRepository.save(new Product(12d, 50l, 1000000l));            
            ruleRepository.save(new Rule(Rule.Target.SALARY, Rule.Relation.GREATER_OR_EQUAL, 25000d, p));
        }
    }
    
}
