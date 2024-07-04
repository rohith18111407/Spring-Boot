package com.ProductManagement.beststore.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// Step-1 All request should be authenticated
		http.authorizeHttpRequests()
				.requestMatchers("/h2-console/**").permitAll()
				.anyRequest().authenticated()
			;
		http.headers().frameOptions().sameOrigin();	//H2 uses frames but SecurityFilterChain does not allow frames, so this snippet permits
		
		//Step-2 If a request is not authenticated, a web page is shown by default (Login page),a popup is shown asking for username and password
		http.httpBasic(withDefaults());
		
		//Step-3 Disable CSRF -> so that you can send POST, PUT methods
		http.csrf().disable();
		
		// Session will not be created
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		
		return http.build();
	}
}
