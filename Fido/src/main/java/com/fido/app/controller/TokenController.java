package com.fido.app.controller;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fido.app.entity.User_Vendor_Auth;
import com.fido.app.helper.JwtUtil;
import com.fido.app.model.JwtRequest;
import com.fido.app.model.JwtResponse;
import com.fido.app.repository.UserVendorAuthRepo;
import com.fido.app.services.CustomUserDetailService;


@RestController
public class TokenController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserVendorAuthRepo userAuthRepo;
	
	
	
	
	@GetMapping(value = "/test")
	public String getTest() {
		return "test Successfull";
	}
	
	
	@PostMapping(value="/token")
	public ResponseEntity<?> getToken(@RequestBody JwtRequest jwtRequest) throws Exception  {
		System.out.println(jwtRequest);
		try {
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword()));
		}catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Bad Credentials");
		}
		
		UserDetails userDetails= this.customUserDetailService.loadUserByUsername(jwtRequest.getEmail());
		User_Vendor_Auth userVendorAuth =userAuthRepo.findByEmail(jwtRequest.getEmail()).orElseThrow();
		
		
		
		String token = jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token,new ArrayList<>(userVendorAuth.getRoles()).get(0).getRole(),userVendorAuth.getFullName(),jwtRequest.getEmail()));
	}

}
