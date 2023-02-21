package com.fido.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fido.app.entity.CustomerDetails;

public interface CustomerRepo extends JpaRepository<CustomerDetails, Long> {

}
