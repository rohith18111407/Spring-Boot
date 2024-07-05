package com.ProductManagement.beststore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ProductManagement.beststore.exception.AuthenticationException;
import com.ProductManagement.beststore.models.AuthenticationRequest;
import com.ProductManagement.beststore.models.AuthenticationResponse;
import com.ProductManagement.beststore.security.JwtUtil;

@Controller
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired 
	private UserDetailsService userDetailsService;
	
	@Autowired 
	private JwtUtil jwtTokenUtil;
	
	//Authentication end point
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException {
		
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
			
		}catch(Exception ex) {
			throw new AuthenticationException(ex.getMessage());
		}
		
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		
		//create JWT Token
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		
		return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.ACCEPTED);	// returns 200OK if authentication succesful
	}
}
