package com.fido.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fido.app.entity.CustomerDetails;

public interface CustomerRepo extends JpaRepository<CustomerDetails, Long> {
      Optional<CustomerDetails> findByEmail(String email);
}
