package com.ProductManagement.beststore.models;

//Response will be the JWT token
public class AuthenticationResponse {
	private final String jwt;
	public AuthenticationResponse(String jwt) {
		this.jwt = jwt;
	}
	public String getJwt() {
		return jwt;
	}
}
