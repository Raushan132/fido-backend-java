package com.fido.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fido.app.entity.CustomerDetails;
import com.fido.app.entity.VendorDetails;
import com.fido.app.repository.CustomerRepo;
import com.fido.app.repository.VendorRepo;
import com.fido.app.services.Extract_Customer_Vendor;

@RestController
public class UserController {

	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired 
	private VendorRepo vendorRepo;

	@Autowired
	private Extract_Customer_Vendor extCustomer_Vendor;

	
	@GetMapping(value="/userProfile")
	public CustomerDetails getUserProfile() {
		
		return extCustomer_Vendor.extract(customerRepo.findById(1L).orElseThrow());
	}
	
	@GetMapping(value = "/userProfile/{id}")
	public CustomerDetails getUserProfileById(@PathVariable("id") long id) {

		return extCustomer_Vendor.extract(customerRepo.findById(id).orElseThrow());

	}
	
	@GetMapping("/vendorProfile")
	public VendorDetails getVendorProfile() {
		
		 return extCustomer_Vendor.extract(vendorRepo.findById(1L).orElseThrow());
	}
	
	@GetMapping("/vendorProfile/{id}")
	public VendorDetails getVendorProfileById(@PathVariable("id") long id) {
		
		 return extCustomer_Vendor.extract(vendorRepo.findById(id).orElseThrow());
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
