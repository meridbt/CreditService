package com.factoringplus.testing.Repository;

import java.util.List;

import com.factoringplus.testing.Model.Product;
import com.factoringplus.testing.Model.Rule;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends CrudRepository<Rule, Long> {
    
    public List<Rule> findAllByProduct(Product product);

    public List<Rule> findAllByEnabled(Boolean enabled);

}
