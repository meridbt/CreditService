package com.factoringplus.testing.Repository;

import java.util.List;

import com.factoringplus.testing.Model.Product;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
    
    public List<Product> findAllByEnabled(Boolean enabled);

}
