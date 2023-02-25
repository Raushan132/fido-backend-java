package com.fido.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fido.app.entity.CustomerDetails;
import com.fido.app.repository.CustomerRepo;
import com.fido.app.services.Extract_Customer_Vendor;

@RestController
public class UserController {

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private Extract_Customer_Vendor extCustomer_Vendor;

	
	
	@GetMapping(value = "/userProfile/{id}")
	public CustomerDetails getUserProfile(@PathVariable("id") long id) {

		return extCustomer_Vendor.extract(customerRepo.findById(id).orElseThrow());

	}

	@PutMapping(value = "/userProfile")
	public String upadateUserProfile(@RequestBody CustomerDetails customerDetail) {
        
		System.out.println(customerDetail);
		System.out.println(customerDetail.getAddress());
		System.out.println(customerDetail.getEmail());
		customerRepo.save(customerDetail);
		return "Data is updated";

	}

}
