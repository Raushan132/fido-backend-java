package com.fido.app.controller;

import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

//import com.cloudinary.Cloudinary;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fido.app.entity.CustomerDetails;
import com.fido.app.entity.Role;
import com.fido.app.entity.UserAuth;
import com.fido.app.entity.VendorDetails;
import com.fido.app.repository.CustomerRepo;
import com.fido.app.repository.UserAuthRepo;
import com.fido.app.repository.VendorRepo;


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
	private UserAuthRepo userAuthRepo;
	
	@Autowired 
	private CustomerRepo cusRepo;
	
	@Autowired
	private VendorRepo vendorRepo;
	
	 @PostMapping("/admin")
	 public String getAdmin(@RequestParam("test") String str,
			 		@RequestParam("file1") MultipartFile file1,
			 		@RequestParam("file2") MultipartFile file2,
			 		@RequestParam(name="file3",required = false ) MultipartFile file3) throws IOException {
		
		 
//		 Map<String,String> config = new HashMap<>();
//		 config.put("cloud_name", "dkw5iydb1");
//		 config.put("api_key", "123235391421445");
//		 config.put("api_secret", "99vLhxjkQ_PmROAlAsaipHXZpUU");
//		 Cloudinary cloudinary = new Cloudinary(config);
//		 try {
////			Map store=  cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
////		    System.out.println(store);
////		    System.out.println(store.get("secure_url"));
//		 } catch (IOException exception) {
//			  System.out.println(exception.getMessage());
//			}
//		 
//		 System.out.println(file.getInputStream());
		
		 
		 ObjectMapper objectMapper= new ObjectMapper();
		   UserAuth userAuth= objectMapper.readValue(str, UserAuth.class);
		 
		 System.out.println(userAuth);
		 
		 return "hello Admin";
		 
	 }
	 
	 @PostMapping("/addUser")
	 public String addUser(@RequestBody UserAuth userAuth) {
		 Role role= new Role();
		 role.setRole("User");
		 userAuth.addRoll(role);
		 System.out.println(userAuth);
		 userAuthRepo.save(userAuth);
		 
		 return "user is added";
	 }
	 
	 @PostMapping("/addCustomer")
	 public String addCustomer(@RequestBody CustomerDetails customerDetails) {
		 
		 System.out.println(customerDetails);
		 System.out.println(customerDetails.getAddress());
		 System.out.println(customerDetails.getEmail());
		 System.out.println(customerDetails.getFullName());
		 Role role =new Role();
		 role.setRole("USER");
		 customerDetails.addRoll(role);
		 cusRepo.save(customerDetails);
		 
		  return "customer is added";
	 }
	 
	 @PostMapping("/addVendor")
	 public String addVendor(@RequestBody VendorDetails vendorDetails) {
		 Role role=new Role();
		 role.setRole("VENDOR");
		 vendorRepo.save(vendorDetails);
		 return "Vendor is added";
	 }

}
