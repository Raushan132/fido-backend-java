package com.fido.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fido.app.entity.User_Vendor_Auth;

public interface UserVendorAuthRepo extends JpaRepository<User_Vendor_Auth, Long> {
	
	public Optional<User_Vendor_Auth> findByEmail(String email);

}
