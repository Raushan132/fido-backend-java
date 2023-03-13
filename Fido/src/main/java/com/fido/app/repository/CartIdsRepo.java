package com.fido.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fido.app.entity.CustProductIds;

public interface CartIdsRepo extends JpaRepository<CustProductIds, Long> {
	Optional<CustProductIds>  findByCustomerIds(long id);
	
	List<CustProductIds> getAllProductIdsByCustomerIds(long id);
	
	long countByCustomerIds(long id);
	
	long countDistinctProductIdsByCustomerIds(long id);
	
	void deleteByCustomerIdsAndProductIds(long cid,long pid);
	void deleteByCustomerIds(long cid);

}
