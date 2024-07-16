package com.ProductManagement.beststore.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/access-denied")
public class FailureHandler {

	public String accessDenied()
	{
		return "UserSection/RegistrationError";
	}
}
