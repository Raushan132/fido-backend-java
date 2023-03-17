package com.fido.app.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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
		 LocalDate date= LocalDate.now();
		 
		 
		 Map<Integer,String> monthSell=new HashMap<>();
				 
		 List<Invoice> invoices= invoiceRepo.findAll();
		 List<Invoice> monthInvoices=    invoiceRepo.findAllByInvoiceDateBetween(date.minusMonths(1).plusDays(1).toString());
		 
		 
		 Function<Invoice,BigDecimal> fun=invoice->new BigDecimal(invoice.getGrandTotal());
		 
		 String totalSell= invoices.stream().map(fun).reduce(BigDecimal.ZERO,BigDecimal::add).toString();
		String todaySell= invoiceRepo.findSumByInvoiceDate(date.toString());
         
		 monthInvoices.forEach(invoice->{
			 LocalDate invoiceDate= LocalDateTime.parse(invoice.getInvoiceDate()).toLocalDate();
			 String total= monthSell.putIfAbsent(invoiceDate.getDayOfMonth(),invoice.getGrandTotal());
			 if(total!=null) monthSell.put(invoiceDate.getDayOfMonth(), new BigDecimal(total).add(new BigDecimal(invoice.getGrandTotal())).toString());
			 
	 });
//		 
		 List<CardDetail>cards=cardRepo.findAll();
		 long activeCard=cards.stream().filter(card->card.isActivate()).count();
		 long deactivatedCard=cards.stream().filter(card->!card.isActivate()).count();
		 
		 if(todaySell==null) todaySell="0";
		 if(totalSell==null) totalSell="0";
		 
		 
		 Map<String,Object> map=new HashMap<>();
		 map.put("customers",noOfCustomers);
		 map.put("vendors",noOfVendors);
		 map.put("product",noOfProducts);
		 map.put("totalSell",String.format("%.2f",Double.parseDouble(totalSell)));
		 map.put("activeCard",activeCard);
		 map.put("deactivatedCard",deactivatedCard);
		 map.put("todaySell",String.format("%.2f",Double.parseDouble(todaySell)));
		 map.put("monthSell",monthSell);
		   
		 return map;
		 
	}
}
