package com.telusko.demo.run;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RunController {
	
	@GetMapping("/hello")
	String home() {
		return "Hello, Runnerz";
	}

}
