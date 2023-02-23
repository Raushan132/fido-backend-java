package com.fido.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fido.app.entity.CustomerDetails;
import com.fido.app.repository.CustomerRepo;

@RestController
public class UserController {

	@Autowired
	private CustomerRepo customerRepo;

	@GetMapping(value = "/userProfile/{id}")
	public CustomerDetails getUserProfile(@PathVariable("id") long id) {

//		var userDetail=userRepo.findById(id).orElseThrow();
//		System.out.println();

		return customerRepo.findById(id).orElseThrow();
	}
	
	@GetMapping(value="/userProfile")
	public List<CustomerDetails> getAllUserProfile(){
		  
		  System.out.println("here:31"+customerRepo.findAll());
		  return customerRepo.findAll();
	}

	@PutMapping(value = "/updateUserProfile")
	public String upadateUserProfile(@RequestBody CustomerDetails customerDetail) {

		customerRepo.save(customerDetail);

		return "Data is updated";

	}

}
