package com.ProductManagement.beststore.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ProductManagement.beststore.models.Product;
import com.ProductManagement.beststore.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
    private ProductRepository productRepository;

    public List<Product> searchProductsByName(String productName) {
        // Call the custom repository method
        return productRepository.findByNameIgnoreCaseContaining(productName);
    }
    
    public Page<Product> getPaginatedProjects(Pageable pageable) {
	    return productRepository.findAll(pageable);
	}
}
