package com.fido.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fido.app.entity.VendorProduct;

public interface VendorProductReop extends JpaRepository<VendorProduct, Long> {
	List<VendorProduct> findAllByProuductOwnerId(long id);
}
