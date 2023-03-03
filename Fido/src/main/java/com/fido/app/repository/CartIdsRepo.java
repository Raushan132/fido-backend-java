package com.fido.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fido.app.entity.CustProductIds;

public interface CartIdsRepo extends JpaRepository<CustProductIds, Long> {
	Optional<CustProductIds>  findByCustomerId(long id);
	       void deleteByProductIdsAndCustomerId(long pid,long cid);
}
