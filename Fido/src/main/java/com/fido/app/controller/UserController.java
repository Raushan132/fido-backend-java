package com.fido.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fido.app.entity.CustomerDetails;
import com.fido.app.entity.VendorDetails;
import com.fido.app.repository.CustomerRepo;
import com.fido.app.repository.VendorRepo;
import com.fido.app.services.AuthDetail;
import com.fido.app.services.Extract_Customer_Vendor;

@RestController
public class UserController {

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private VendorRepo vendorRepo;
	
	@Autowired
	private AuthDetail authDetail;

	@Autowired
	private Extract_Customer_Vendor extCustomer_Vendor;

	@GetMapping(value = "/userProfile")
	public CustomerDetails getUserProfile() {
		
		return extCustomer_Vendor.extract(authDetail.getCustomerDetail());
	}

	@PutMapping(value = "/userProfile")
	public String upadateUserProfile(@RequestBody CustomerDetails customerDetail) {

		System.out.println(customerDetail);
		System.out.println(customerDetail.getAddress());
		System.out.println(customerDetail.getEmail());
		var temp = customerRepo.findById(customerDetail.getId()).orElseThrow();
		customerDetail.setPassword(temp.getPassword());
		customerDetail.setRoles(temp.getRoles());

		customerRepo.save(customerDetail);
		return "Data is updated";

	}

	@GetMapping("/vendorProfile")
	public VendorDetails getVendorProfile() {
		
		return extCustomer_Vendor.extract(authDetail.getVendorDetail());
	}

	@PutMapping(value = "/vendorProfile")
	public String upadateVendorProfile(@RequestBody VendorDetails vendorDetail) {

		System.out.println("Vendor is updating");
		var temp = vendorRepo.findById(vendorDetail.getId()).orElseThrow();
		vendorDetail.setPassword(temp.getPassword());
		vendorDetail.setRoles(temp.getRoles());

		vendorRepo.save(vendorDetail);
		return "Data is updated";
	}

}
