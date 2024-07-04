package com.ProductManagement.beststore.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import com.ProductManagement.beststore.models.Product;

@Service
public class ProductDaoService {
	private static List<Product> products=new ArrayList<>();
	
	public List<Product> findAll()
	{
		return products;
	}
	
	public Product findById(int id)
	{
		Predicate<? super Product> predicate=product->product.getId().equals(id);
		return products.stream().filter(predicate).findFirst().orElse(null);
	}
	
	public void deleteById(int id)
	{
		Predicate<? super Product> predicate=product->product.getId().equals(id);
		products.removeIf(predicate);
	}
	
	public Product save(Product product)
	{
		products.add(product);
		return product;
	}
	
}




//insert into product(id,brand,name,category,description,price)
//values(1,'Asus','Zenbook','Laptop','Intel i5 12thGen',98000);
//
//insert into product(id,brand,name,category,description,price)
//values(2,'Asus','TUF Gaming','Laptop','Gaming Processor',94000);
//
//insert into product(id,brand,name,category,description,price)
//values(3,'Poco','F4','Phone','Snapdragon 8',32000);
//
//insert into product(id,brand,name,category,description,price)
//values(4,'Poco','M3','Phone','Snapdragon 7',14000);



