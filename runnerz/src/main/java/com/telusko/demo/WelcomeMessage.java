package com.telusko.demo;

import org.springframework.stereotype.Component;

@Component
public class WelcomeMessage {
	
	public String getWelcomeMessage() {
		return "Welcome to the SpringBoot Application";
	}
}
