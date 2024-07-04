package com.in28minutes.rest.webservices.restful_web_services.security;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// Step-1 All request should be authenticated
		http.authorizeHttpRequests(
				auth->auth.anyRequest().authenticated()
				);
		
		//Step-2 If a request is not authenticated, a web page is shown by default (Login page),a popup is shown asking for username and password
		http.httpBasic(withDefaults());
		
		//Step-3 Disable CSRF -> so that you can send POST, PUT methods
		http.csrf().disable();
		
		return http.build();
	}
}
