package com.fido.app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fido.app.entity.CardDetail;
import com.fido.app.entity.CustomerDetails;
import com.fido.app.entity.Role;
import com.fido.app.entity.VendorDetails;
import com.fido.app.exception.InvalidException;
import com.fido.app.repository.CustomerRepo;
import com.fido.app.repository.VendorRepo;
import com.fido.app.services.AuthDetail;
import com.fido.app.services.Extract_Customer_Vendor;
import com.fido.app.services.UploadFile;


/**
 * <p>
 *   all the Admin Controls like 
 *   add User/Vendor
 * </p>
 * @author Raushan Kumar
 *
 */
@RestController
@RequestMapping("/api")
public class AdminController {
	
	
	@Autowired
	private AuthDetail authDetail;
	
	@Autowired 
	private CustomerRepo cusRepo;
	
	@Autowired
	private VendorRepo vendorRepo;
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private Extract_Customer_Vendor extCustomer_Vendor;
	
	@Autowired
	private UploadFile uploadFile;
	
	
//	 user/ customer data get put post below...
	
	@GetMapping(value = "/users")
	public List<CustomerDetails> getAllUserProfile() throws InvalidException {
        
		if(authDetail.isUser())
			throw new InvalidException("Invalid User");
		
		
		return customerRepo.findAll().stream()
				.filter(customer -> customer.getRoles().stream().allMatch(role -> role.getRole().equals("USER")))
				.map(customer -> {
					return extCustomer_Vendor.extract(customer);
				}).collect(Collectors.toList());

		
	}
	
	 @GetMapping(value = "/userProfile/{id}")
		public CustomerDetails getUserProfileById(@PathVariable("id") long id) {
			return extCustomer_Vendor.extract(customerRepo.findById(id).orElseThrow());

		}
	 
	 @PostMapping("/customer")
	 public String addCustomer(@RequestParam String customer,
		 		@RequestParam("aadhar") MultipartFile file1,
		 		@RequestParam("pan") MultipartFile file2) throws JsonMappingException, JsonProcessingException {
		 
		 System.out.println(customer);
		
		 // change string JSON to customer details...
		 ObjectMapper objectMapper= new ObjectMapper();
		 CustomerDetails custDetails= objectMapper.readValue(customer,CustomerDetails.class);
		 
		 //send files to the CLoudinary api...
		 String url=uploadFile.getUploadFile(file1);
		 custDetails.setUrlAadhar(url);

		 url=uploadFile.getUploadFile(file2);
		 custDetails.setUrlPanCard(url);
		
		 Role role =new Role();
		 role.setRole("USER");
		 custDetails.addRoll(role);
		 cusRepo.save(custDetails);
		 
		  return "customer is added";
	 }
		
	
//	 vendor data get put post below...

	@GetMapping(value = "/vendors")
	public List<VendorDetails> getAllVendors() throws InvalidException {
		
		if(!authDetail.isAdmin())
				throw new InvalidException("Invalid USER");
		
		return vendorRepo.findAll().stream()
				.filter(vendor -> vendor.getRoles().stream().allMatch(role -> role.getRole().equals("VENDOR")))
				.map(vendor -> {
					return extCustomer_Vendor.extract(vendor);
				}).collect(Collectors.toList());

	}
	
	 @GetMapping("/vendorProfile/{id}")
		public VendorDetails getVendorProfileById(@PathVariable("id") long id) {
			
			 return extCustomer_Vendor.extract(vendorRepo.findById(id).orElseThrow());
		}
	
	
	 
	 
	 
	
	 
	 @PostMapping("/vendor")
	 public String addVendor(@RequestParam("vendor") String vendor,
			 @RequestParam("aadhar") MultipartFile file1,
			 @RequestParam("pan") MultipartFile file2,
			 @RequestParam(name="doc",required = false) MultipartFile file3) throws JsonMappingException, JsonProcessingException {
		 
		 
		 
		 ObjectMapper objectMapper= new ObjectMapper();
		 VendorDetails vendorDetails= objectMapper.readValue(vendor,VendorDetails.class);
		 
//		 send files to the CLoudinary api...
		   String url= uploadFile.getUploadFile(file1);
		   vendorDetails.setUrlAadhar(url);
		   
		   url = uploadFile.getUploadFile(file2);
		   vendorDetails.setUrlPanCard(url);
			
			if(file3 !=null) {
				url = uploadFile.getUploadFile(file3);
				   vendorDetails.setUrlBusinessDoc(url);
			}
		
		 
		 Role role=new Role();
		 role.setRole("VENDOR");
		 vendorDetails.addRoll(role);
		 vendorRepo.save(vendorDetails);
		 return "Vendor is added";
	 }
	 

}
