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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ProductManagement.beststore.models.Product;
import com.ProductManagement.beststore.models.ProductCreation;
import com.ProductManagement.beststore.repository.ProductRepository;

import jakarta.validation.Valid;

@Controller
public class UserController {

	@Autowired
	private ProductRepository repository;
	
	@GetMapping("/user/jpa/products")
	public String showProductList(Model model)
	{
		
		List<Product> products=repository.findAll();
		model.addAttribute("products",products);	//products list added to model products will be accessible to index.html
		return "UserSection/userHomePage";	//products folder present in the templates folder having index.html within it
	}
	
	@GetMapping("/user/about")
	public String showAboutPage()
	{
		return "UserSection/userAboutPage";
	}
	
	@GetMapping("/user/contact")
	public String showContactPage()
	{
		return "UserSection/userContactPage";
	}
}
