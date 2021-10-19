package com.factoringplus.testing.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.factoringplus.testing.DTO.ClaimDTO;
import com.factoringplus.testing.Model.Product;
import com.factoringplus.testing.Model.Rule;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;

@Service
public class CreditFilter {
    
    public static List<Product> filterProducts(Map<Product, List<Rule>> products, ClaimDTO claim) {
        return products.entrySet().stream()
        .filter(x -> x.getKey().getSumLimit() == null || x.getKey().getSumLimit() >= claim.getClaim())
        .filter(x -> checkRules(x.getValue(), claim))
        .map(x -> x.getKey()).collect(Collectors.toList());
    }

    private static Boolean checkRules(List<Rule> rules, ClaimDTO claim) {
        return rules.stream().filter(x -> !checkRule(x, claim)).count() == 0;
    }

    private static Boolean checkRule(Rule rule, ClaimDTO claim) {
        switch (rule.getFilter()) {
            case SALARY_NOT_LESS: return rule.getValue() <= claim.getSalary();
            case IS_DEBTOR: return (rule.getValue() > 0) == claim.getIsDebtor();
            default: throw new NotYetImplementedException();
        }
    }

}
