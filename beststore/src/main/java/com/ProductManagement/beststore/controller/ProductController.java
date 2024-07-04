package com.ProductManagement.beststore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ProductManagement.beststore.models.Product;
import com.ProductManagement.beststore.repository.ProductRepository;

@RestController
public class ProductController {
	
	@Autowired
	ProductRepository repo;
	
	//get all the products
	@GetMapping("/products")
	public List<Product> getAllProducts()
	{
		List<Product> products=repo.findAll();
		return products;
	}
	
	
	//get product based on id
	@GetMapping("/products/{id}")
	public Product getProduct(@PathVariable int id)
	{
		Product product=repo.findById(id).get();
		return product;
	}
	
	
	//add product
	@PostMapping("/products/add")
	@ResponseStatus(code=HttpStatus.CREATED)	//change status 200 OK to status 201 CREATED
	public void createProduct(@RequestBody Product product)
	{
		repo.save(product);
	}
	
	//update product with given id
	@PutMapping("/products/update/{id}")
	public Product updateProduct(@PathVariable int id)
	{
		Product product=repo.findById(id).get();
		product.setPrice(product.getPrice()+product.getPrice()*0.1);		// price hike 10%
		repo.save(product);
		return product;
	}
	
	//delete product by id
	@DeleteMapping("/products/delete/{id}")
	public void deleteProduct(@PathVariable int id)
	{
		Product product=repo.findById(id).get();
		repo.delete(product);
	}
	
}
