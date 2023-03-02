package com.fido.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fido.app.entity.InvoiceProduct;

@Repository
public interface InvoiceRepo extends JpaRepository<InvoiceProduct, Long>{

}
