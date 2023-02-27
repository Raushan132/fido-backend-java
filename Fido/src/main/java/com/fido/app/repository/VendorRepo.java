package com.fido.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fido.app.entity.VendorDetails;

public interface VendorRepo extends JpaRepository<VendorDetails, Long> {
	Optional<VendorDetails> findByEmail(String email);
}
