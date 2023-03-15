package com.fido.app.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class JwtRequest {
	@Email(message = "Invalid data")
	private String email;
	@NotBlank(message="Invalid data")
	private String password;
}
