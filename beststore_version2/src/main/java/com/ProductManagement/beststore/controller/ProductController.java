package com.ProductManagement.beststore.controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ProductManagement.beststore.models.Product;
import com.ProductManagement.beststore.models.ProductCreation;
import com.ProductManagement.beststore.repository.ProductRepository;

import jakarta.validation.Valid;

@Controller
public class ProductController {
	
	@Autowired
	private ProductRepository repository;
	
	public ProductController(ProductRepository repository)
	{
		this.repository=repository;
	}
	
	// returns the name of the html file to be returned
	@GetMapping("/jpa/products")
	public String showProductList(Model model)
	{
		List<Product> products=repository.findAll();
		model.addAttribute("products",products);	//products list added to model products will be accessible to index.html
		return "products/index";	//products folder present in the templates folder having index.html within it
	}
	
	//endpoint name and the .html page name should be different
	@GetMapping("/jpa/products/create")
	public String showCreatePage(Model model)
	{
		ProductCreation productCreation=new ProductCreation();
		model.addAttribute("productCreation",productCreation);
		return "products/CreateProduct";
	}
	
	//submit button requires POST, need to validate the object of ProductCreation hence @valid is used, to check if there is any validation error add parameter BindingResult
	@PostMapping("/jpa/products/create")
	public String createProduct(@Valid @ModelAttribute ProductCreation productCreation,BindingResult result)
	{
		//check whether the productCreation object has error related to imageFile beeing uploaded in form
		if(productCreation.getImageFile().isEmpty())
		{
			result.addError(new FieldError("productCreation","imageFile","The image file is required"));
		}
		
		//check whether there is any error
		if(result.hasErrors())
		{
			return "products/CreateProduct";
		}
		
		//save image file
		MultipartFile image=productCreation.getImageFile();
		String storageFileName=image.getOriginalFilename();
		
		try
		{
			String uploadDir="public/images/";
			Path uploadPath=Paths.get(uploadDir);
			
			if(!Files.exists(uploadPath))
			{
				Files.createDirectories(uploadPath);
			}
			
			try(InputStream inputStream = image.getInputStream())
			{
				Files.copy(inputStream, Paths.get(uploadDir+storageFileName),StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch(Exception ex)
		{
			System.out.println("Exception: "+ex.getMessage());
		}
		
		
		// save the product in database
		Product product=new Product();
		product.setName(productCreation.getName());
		product.setBrand(productCreation.getBrand());
		product.setCategory(productCreation.getCategory());
		product.setPrice(productCreation.getPrice());
		product.setDescription(productCreation.getDescription());
		product.setImageFileName(storageFileName);
		
		repository.save(product);
		
		return "redirect:/jpa/products";
	}
	
	//update the product
	@GetMapping("/jpa/products/update")
	public String showEditPage(Model model, @RequestParam int id)
	{
		try {
			Product product=repository.findById(id).get();
			model.addAttribute("product",product);
			
			ProductCreation productCreation=new ProductCreation();
			productCreation.setName(product.getName());
			productCreation.setBrand(product.getBrand());
			productCreation.setCategory(product.getCategory());
			productCreation.setPrice(product.getPrice());
			productCreation.setDescription(product.getDescription());
			
			model.addAttribute("productCreation",productCreation);
		}
		catch(Exception ex)
		{
			System.out.println("Exception: "+ex.getMessage());
			return "redirect:/jpa/products";				// Use / to append after localhost:8081
		}
		return "products/EditProduct";
	}
	
	@PostMapping("/jpa/products/update")
	public String updateProduct(Model model,@RequestParam int id,@Valid @ModelAttribute ProductCreation productCreation,BindingResult result)
	{
		try	//connecting to the database hence use try...catch() block
		{
			Product product=repository.findById(id).get();
			model.addAttribute("product",product);		//reading product details from page
			
			if(result.hasErrors())
			{
				return "products/EditProduct";
			}
			
			//check whether there is a new image added
			if(!productCreation.getImageFile().isEmpty())
			{
				//delete the old image file
				String uploadDir="public/images/";
				Path oldImagePath=Paths.get(uploadDir+product.getImageFileName()); //previous filename is available at product object in the database
				
				try
				{
					Files.delete(oldImagePath);
				}
				catch(Exception ex)
				{
					System.out.println("Exception: "+ex.getMessage());
				}
				
				//save new Image file
				MultipartFile image=productCreation.getImageFile();	
				String storageFileName=image.getOriginalFilename();
				
				try(InputStream inputStream = image.getInputStream())
				{
					Files.copy(inputStream, Paths.get(uploadDir+storageFileName),StandardCopyOption.REPLACE_EXISTING);
				}
				
				product.setImageFileName(storageFileName);
				
				// save the product in database
				product.setName(productCreation.getName());
				product.setBrand(productCreation.getBrand());
				product.setCategory(productCreation.getCategory());
				product.setPrice(productCreation.getPrice());
				product.setDescription(productCreation.getDescription());
						
				repository.save(product);
				
			}
			
			
		}
		catch(Exception ex)
		{
			System.out.println("Exception : "+ex.getMessage());
		}
		
		return "redirect:/jpa/products";
	}
	
	
	@GetMapping("/jpa/products/delete")
	public String deleteProduct(@RequestParam int id)
	{
		try
		{
			Product product=repository.findById(id).get();
			
			//delete the product image from public/images folder
			Path imagePath=Paths.get("public/images/"+product.getImageFileName());
			
			try
			{
				Files.delete(imagePath);
			}
			catch(Exception ex)
			{
				System.out.println("Exception: "+ex.getMessage());
			}
			
			//delete the product from database
			repository.delete(product);
			
		}
		catch(Exception ex)
		{
			System.out.println("Exception: "+ex.getMessage());
		}
		
		return "redirect:/jpa/products";
	}
	
	
	//get all the products
//	@GetMapping("/jpa/products")
//	public List<Product> getAllProducts()
//	{
//		List<Product> products=repository.findAll();
//		return products;
//	}
	

	//get product based on id
//	@GetMapping("/jpa/products/{id}")
//	public Product getProduct(@PathVariable int id)
//	{
//		Product product=repository.findById(id).get();
//		return product;
//	}
//	
	
	//add product
//	@PostMapping("/jpa/products/add")
//	@ResponseStatus(code=HttpStatus.CREATED)	//change status 200 OK to status 201 CREATED
//	public void createProduct(@RequestBody Product product)
//	{
//		repository.save(product);
//	}
	
	
	//update product with given id
//	@PutMapping("/jpa/products/update/{id}")
//	public Product updateProduct(@PathVariable int id)
//	{
//		Product product=repository.findById(id).get();
//		product.setPrice(product.getPrice()+product.getPrice()*0.1);		// price hike 10%
//		repository.save(product);
//		return product;
//	}
	
	
	//delete product by id
//	@DeleteMapping("/jpa/products/delete/{id}")
//	public void deleteProduct(@PathVariable int id)
//	{
//		Product product=repository.findById(id).get();
//		repository.delete(product);
//	}
	
}