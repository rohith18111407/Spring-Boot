package com.telusko.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.telusko.demo.dao.AlienRepo;
import com.telusko.demo.model.Alien;

@Controller
public class AlienController {
	
	@Autowired
	private AlienRepo repo;
	
	@RequestMapping("/home")
	public String home() {
		return "home.jsp";
	}
	
//	public String addAlien( @RequestParam int aid,@RequestParam String aname)

	@PostMapping("/addAlien")
	public String addAlien( @RequestBody Alien alien)
	{
		Alien savedAlien = repo.save(alien);
		return "home.jsp";	
	}
	
	
}
