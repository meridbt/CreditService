package com.factoringplus.testing.Controller;

import java.net.URI;
import java.util.stream.Collectors;

import com.factoringplus.testing.DTO.ClaimDTO;
import com.factoringplus.testing.DTO.ProductDTO;
import com.factoringplus.testing.DTO.ProductDetailstDTO;
import com.factoringplus.testing.DTO.RuleDTO;
import com.factoringplus.testing.Model.Rule;
import com.factoringplus.testing.Repository.ProductRepository;
import com.factoringplus.testing.Repository.RuleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.var;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    RuleRepository ruleRepository;

    @GetMapping("")
    public @ResponseBody Iterable<ProductDTO> getProducts() {
        return productRepository.findAllByEnabled(true).stream().map(x -> x.toDto()).collect(Collectors.toList());
    }

    @GetMapping("/{id}/rules")
    public ResponseEntity<ProductDetailstDTO> getProductDetails(@PathVariable("id") Long id) {
        try {
            var product = productRepository.findById(id);
            if (!product.isPresent()) return ResponseEntity.notFound().build(); 
            var rules = ruleRepository.findAllByProduct(product.get()).stream()
                .filter(x -> x.getEnabled()).map(x -> x.toDto()).collect(Collectors.toList());
            return ResponseEntity.ok().body(new ProductDetailstDTO(product.get(), rules));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{id}/rules")
    public ResponseEntity<Object> saveRule(@PathVariable("id") Long id, @RequestBody RuleDTO rule) {
        try {
            var product = productRepository.findById(id);
            if (!product.isPresent()) return ResponseEntity.notFound().build();
            var newRule = ruleRepository.save(new Rule(rule, product.get()));
            if (newRule == null) return ResponseEntity.unprocessableEntity().build();
            return ResponseEntity.created(new URI(String.format("/products/%d/rules", id))).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{product_id}/rules/{id}")
    public ResponseEntity<Object> deleteRule(@PathVariable("id") Long id) {
        try {
            var ruleOpt = ruleRepository.findById(id);
            if (!ruleOpt.isPresent()) return ResponseEntity.notFound().build();
            var rule = ruleOpt.get();
            rule.setEnabled(false);
            ruleRepository.save(rule);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/apply")
    public ResponseEntity<Object> applyClaim(@RequestBody ClaimDTO claim) {
        try {
            var rules = ruleRepository.findAllByEnabled(true);
            return ResponseEntity.ok().body(rules.stream().collect(Collectors.groupingBy(x -> x.getProduct())));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
}
