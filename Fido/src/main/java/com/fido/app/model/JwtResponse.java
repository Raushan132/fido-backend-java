package com.fido.app.model;

import lombok.Data;

@Data
public class JwtResponse {
	private String token;
	private String role;
	private String name;
	private String email;
	public JwtResponse(String token, String role,String name,String email) {
		this.token= token;
		this.role=role;
		this.name=name;
		this.email=email;
	}
}
