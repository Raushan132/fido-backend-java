package com.fido.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fido.app.entity.Invoice;

@Repository
public interface InvoiceRepo extends JpaRepository<Invoice, Long>{
     List<Invoice> findAllBycEmail(String email);
}
