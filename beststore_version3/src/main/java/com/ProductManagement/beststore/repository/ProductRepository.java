package com.ProductManagement.beststore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ProductManagement.beststore.models.Product;

public interface ProductRepository extends JpaRepository<Product,Integer>{
	 List<Product> findByNameIgnoreCaseContaining(String name);
}
