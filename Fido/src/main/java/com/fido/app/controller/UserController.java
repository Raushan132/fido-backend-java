package com.fido.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fido.app.entity.CustomerDetails;
import com.fido.app.entity.Invoice;
import com.fido.app.entity.VendorDetails;
import com.fido.app.model.Response;
import com.fido.app.repository.CustomerRepo;
import com.fido.app.repository.VendorRepo;
import com.fido.app.services.AuthDetail;
import com.fido.app.services.Extract_Customer_Vendor;
import com.fido.app.services.InvoiceGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserController {

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private VendorRepo vendorRepo;
	
	@Autowired
	private AuthDetail authDetail;
	
	@Autowired
	private InvoiceGenerator invoiceGenerator;

	@Autowired
	private Extract_Customer_Vendor extCustomer_Vendor;

	@GetMapping(value = "/userProfile")
	public CustomerDetails getUserProfile() {
		
		return extCustomer_Vendor.extract(authDetail.getCustomerDetail());
	}
	
	@GetMapping("/invoices/{cid}")
	public List<Invoice> getAllInvoiceById(@PathVariable("cid") long id){
	 String email=	authDetail.getCustomerDetailsById(id).getEmail();
	 
		return invoiceGenerator.getInvoiceByCustomerEmail(email);
	 
	}
	
	@GetMapping("/invoices")
	public List<Invoice>getAllInvoiceByCustomerEmail(){
		return invoiceGenerator.getInvoiceByCustomerEmail(authDetail.getCustomerDetail().getEmail());
	}

	@PutMapping(value = "/userProfile")
	public ResponseEntity<?> upadateUserProfile(@Valid @RequestBody CustomerDetails customerDetail) {

		
		var temp = authDetail.getCustomerDetailsById(customerDetail.getId());
		customerDetail.setPassword(temp.getPassword());
		customerDetail.setRoles(temp.getRoles());

		customerRepo.save(customerDetail);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("201","Customer Detail is updated"));

	}

	@GetMapping("/vendorProfile")
	public VendorDetails getVendorProfile() throws Exception {
		
		return extCustomer_Vendor.extract(authDetail.getVendorDetail());
	}

	@PutMapping(value = "/vendorProfile")
	public ResponseEntity<?> upadateVendorProfile(@Valid @RequestBody VendorDetails vendorDetail) {

		log.info("Vendor is updating");
		var temp = authDetail.getVendorDetailById(vendorDetail.getId());
		vendorDetail.setPassword(temp.getPassword());
		vendorDetail.setRoles(temp.getRoles());

		vendorRepo.save(vendorDetail);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("201","Vendor Detail is updated"));

	}

}
