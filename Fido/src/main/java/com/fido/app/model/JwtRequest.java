package com.fido.app.model;

import lombok.Data;

@Data
public class JwtRequest {
	private String email;
	private String password;
}
