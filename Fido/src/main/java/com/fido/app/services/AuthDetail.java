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

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
                 log.info("No Customer is available in AuthDetail class");
                 throw new NoSuchElementException();
		}
	}

	public VendorDetails getVendorDetail() throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		try {
		return vendorRepo.findByEmail(auth.getName()).orElseThrow();
		}catch(Exception e) {

			throw new Exception("vendor is not find");
		}
	}
	
	public VendorDetails getVendorDetailById(long id) {
		try {
		return vendorRepo.findById(id).orElseThrow();
		}catch(NoSuchElementException exception) {
			log.info("AuthDetail vendor is not found");
			throw exception;
		}
	}
	
	public CustomerDetails getCustomerDetailsById(long id) {
		try {
		return customerRepo.findById(id).orElseThrow();
		}catch(NoSuchElementException exception) {
			log.info("AuthDetail Customer is not found");
			throw exception;
		}
	}

}
