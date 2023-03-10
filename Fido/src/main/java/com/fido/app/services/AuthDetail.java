package com.fido.app.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fido.app.entity.CustomerDetails;
import com.fido.app.entity.VendorDetails;
import com.fido.app.repository.CustomerRepo;
import com.fido.app.repository.VendorRepo;

@Service
public class AuthDetail {

	@Autowired
	private CustomerRepo customerRepo;
	@Autowired
	private VendorRepo vendorRepo;

	public boolean isAdmin() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ADMIN"));
	}

	public boolean isVendor() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("VENDOR"));
	}

	public boolean isUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("USER"));
	}

	public CustomerDetails getCustomerDetail() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		try {
			return customerRepo.findByEmail(auth.getName()).orElseThrow();
		} catch (NoSuchElementException e) {
                 System.out.println("No Customer is available in AuthDetail class");
                 throw new NoSuchElementException();
		}
	}

	public VendorDetails getVendorDetail() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return vendorRepo.findByEmail(auth.getName()).orElseThrow();

	}
	
	public VendorDetails getVendorDetailById(long id) {
		try {
		return vendorRepo.findById(id).orElseThrow();
		}catch(NoSuchElementException exception) {
			System.out.println("AuthDetail vendor is not found");
			throw exception;
		}
	}
	
	public CustomerDetails getCustomerDetailsById(long id) {
		try {
		return customerRepo.findById(id).orElseThrow();
		}catch(NoSuchElementException exception) {
			System.out.println("AuthDetail Customer is not found");
			throw exception;
		}
	}

}
