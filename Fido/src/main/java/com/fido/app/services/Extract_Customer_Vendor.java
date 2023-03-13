package com.fido.app.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.fido.app.entity.CustomerDetails;
import com.fido.app.entity.Role;
import com.fido.app.entity.VendorDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Extract_Customer_Vendor {
	
	
	
	
	public CustomerDetails extract(CustomerDetails customerDetails) {
	 
	  customerDetails.setPassword("");
	  Set<Role> roles= new HashSet<>();
	  customerDetails.setRoles(roles);
	  log.info(customerDetails.getEmail());
	  return customerDetails;
	}
	
	public VendorDetails extract(VendorDetails vendorDetails) {
		 
		vendorDetails.setPassword("");
		  Set<Role> roles= new HashSet<>();
		  vendorDetails.setRoles(roles);
		  log.info(vendorDetails.getEmail());
		  return vendorDetails;
		}
	
	

}
