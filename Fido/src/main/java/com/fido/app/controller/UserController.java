package com.fido.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fido.app.entity.User_Detail;
import com.fido.app.repository.UserRepo;


@RestController
public class UserController {
	
	@Autowired
	private UserRepo userRepo;
	
	
	@GetMapping(value="/userProfile/{id}")
	public User_Detail getUserProfile(@PathVariable("id") long id) {
		
//		var userDetail=userRepo.findById(id).orElseThrow();
//		System.out.println(userDetail);
			
		 return userRepo.findById(id).orElseThrow();
	}
	
	@PutMapping(value="/updateUserProfile")
	public String upadateUserProfile(@RequestBody User_Detail userDetail) {
//			System.out.println(userDetail);
			userRepo.save(userDetail);
			
			return "Data is updated";
		 
	}
	
	
	

}
