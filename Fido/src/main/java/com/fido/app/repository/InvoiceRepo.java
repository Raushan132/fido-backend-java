package com.fido.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fido.app.entity.Invoice;

@Repository
public interface InvoiceRepo extends JpaRepository<Invoice, Long>{
     List<Invoice> findAllBycEmail(String email);
     @Query(value = "SELECT  * FROM fido.invoice where str_to_date(invoice_date,'%Y-%m-%d')=?1", nativeQuery = true)
     List<Invoice> findAllByInvoiceDate(String date);
     
     @Query(value = "SELECT  sum(grand_total) FROM fido.invoice where str_to_date(invoice_date,'%Y-%m-%d')=?1", nativeQuery = true)
     String findSumByInvoiceDate(String date);
    
     @Query(value = "SELECT  * FROM fido.invoice where str_to_date(invoice_date,\"%Y-%m-%d\") > '?1'", nativeQuery = true)
     List<Invoice> findAllByInvoiceDateBetween(String date1);
}
