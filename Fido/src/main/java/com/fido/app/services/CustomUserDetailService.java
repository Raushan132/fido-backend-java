package com.fido.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.fido.app.entity.User_Vendor_Auth;
import com.fido.app.repository.UserVendorAuthRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	
	@Autowired
	private UserVendorAuthRepo userVendorAuthRepo;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
	User_Vendor_Auth user_detail=	userVendorAuthRepo.findByEmail(username).orElseThrow();
	return user_detail;
//		if(username.equals("Raka")) {
//			return new User("Raka","Password",new ArrayList<>());
//		}
//		
//		else throw new UsernameNotFoundException(username);
	}

}
