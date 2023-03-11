package com.fido.app.services;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fido.app.entity.CardDetail;
import com.fido.app.entity.Invoice;
import com.fido.app.repository.CardRepo;
import com.fido.app.repository.CustomerRepo;
import com.fido.app.repository.InvoiceRepo;
import com.fido.app.repository.VendorProductReop;
import com.fido.app.repository.VendorRepo;

@Service
public class DashboardService {
    
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private VendorRepo vendorRepo;
	
	@Autowired
	private InvoiceRepo invoiceRepo;
	
	@Autowired
	private VendorProductReop productReop;
	
	@Autowired
	private CardRepo cardRepo;
	
	public Map<String,Object> getAdminDashboardData() {
		 long noOfCustomers=customerRepo.count();
		 long noOfVendors= vendorRepo.count();
		 long noOfProducts=productReop.count();
		 
		 List<Invoice> invoices= invoiceRepo.findAll();
		 String totalSell= invoices.stream().map(invoice->new BigDecimal(invoice.getGrandTotal())).reduce(BigDecimal.ZERO,BigDecimal::add).toString();
		 
		 List<CardDetail>cards=cardRepo.findAll();
		 long activeCard=cards.stream().filter(card->card.isActivate()).count();
		 long deactivatedCard=cards.stream().filter(card->!card.isActivate()).count();
		 
		 Map<String,Object> map=new HashMap<>();
		 map.put("customers",noOfCustomers);
		 map.put("vendors",noOfVendors);
		 map.put("product",noOfProducts);
		 map.put("totalSell",Double.valueOf(totalSell));
		 map.put("activeCard",activeCard);
		 map.put("deactivatedCard",deactivatedCard);
		 
		 
	        
		 return map;
		 
	}
}
