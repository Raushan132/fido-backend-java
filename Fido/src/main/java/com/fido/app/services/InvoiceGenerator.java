package com.fido.app.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fido.app.entity.CardDetail;
import com.fido.app.entity.CustomerDetails;
import com.fido.app.entity.Invoice;
import com.fido.app.entity.InvoiceProduct;
import com.fido.app.entity.VendorDetails;
import com.fido.app.entity.VendorProduct;
import com.fido.app.exception.CardExpireException;
import com.fido.app.exception.OutOfStockException;
import com.fido.app.model.CartProducts;
import com.fido.app.repository.CartIdsRepo;
import com.fido.app.repository.InvoiceRepo;
import com.fido.app.repository.VendorProductReop;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class InvoiceGenerator {

	@Autowired
	private CardService cardService;

	@Autowired
	private InvoiceRepo invoiceRepo;

	@Autowired
	private CartIdsRepo cartRepo;

	@Autowired
	private VendorProductReop productReop;

	public Invoice getInvoiceData(CustomerDetails custDetails, VendorDetails vendor, List<CartProducts> products,
			CardDetail card) throws OutOfStockException,CardExpireException,Exception {
		if (cardService.isCardExpire(card))
			throw new CardExpireException("Card is Expired");

		subtractStock(products);
		
		var invoiceProducts = products.stream().map(product -> convertInvoiceProduct(product))
				.collect(Collectors.toList());
		log.info(invoiceProducts.toString());
		BigDecimal grandTotal = invoiceProducts.stream().map(product -> new BigDecimal(product.getTotalamt()))
				.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_DOWN);

		Invoice invoice = setInvoiceInfo(custDetails, vendor);
		invoice.setProduct(invoiceProducts);
		invoice.setGrandTotal(grandTotal.toString());

		log.info(invoice.toString());

		invoice = buyProduct(invoice, card);

		cartRepo.deleteByCustomerIds(custDetails.getId());
		

		return invoice;
		
	}

	private InvoiceProduct convertInvoiceProduct(CartProducts product) {

		var invoiceProduct = new InvoiceProduct();
		invoiceProduct.setDescription(product.getDescription());
		invoiceProduct.setTitle(product.getTitle());
		invoiceProduct.setPrice(product.getPrice());
		invoiceProduct.setQty(product.getQuantity());
		invoiceProduct.setDiscount(product.getDiscount());
		invoiceProduct.setTax("18");
		String discount = invoiceProduct.getDiscount();
		BigDecimal price = new BigDecimal(invoiceProduct.getPrice());
		BigDecimal qty = new BigDecimal(invoiceProduct.getQty());
		BigDecimal dis = new BigDecimal(discount == null ? "0" : discount);
		BigDecimal tax = new BigDecimal(invoiceProduct.getTax());

//		System.out.println(price+" "+ qty+" "+dis+" "+tax);
		BigDecimal amt = price.multiply(qty);
//		System.out.println("Amount:"+amt);

		amt = amt.subtract(amt.multiply(dis.divide(BigDecimal.valueOf(100.0))));

//		System.out.println("discount amount:"+amt);

		var totAmt = amt.add(amt.multiply(tax.divide(BigDecimal.valueOf(100.0))));
		invoiceProduct.setTotalamt(String.valueOf(totAmt.setScale(2, RoundingMode.HALF_DOWN)));

		return invoiceProduct;
	}

	private Invoice setInvoiceInfo(CustomerDetails customer, VendorDetails vendor) {
		Invoice invoice = new Invoice();
		invoice.setCEmail(customer.getEmail());
		invoice.setC_country(customer.getCountry());
		invoice.setC_city(customer.getCity());
		invoice.setC_state(customer.getState());
		invoice.setC_address(customer.getAddress());
		invoice.setCFullName(customer.getFullName());
		invoice.setCMob(customer.getMobile());

		invoice.setV_country(vendor.getCountry());
		invoice.setV_city(vendor.getCity());
		invoice.setV_state(vendor.getState());
		invoice.setV_email(vendor.getEmail());
		invoice.setV_address(vendor.getAddress());
		invoice.setVFullName(vendor.getFullName());
		invoice.setVMob(vendor.getMobile());
		invoice.setGst(vendor.getGst());

		invoice.setInvoiceDate(LocalDateTime.now().toString());
		invoice.setOrderDate(LocalDateTime.now().toString());

		invoice.setInvoiceNo("IN" + LocalDate.now().getYear() + generateRandom());
		invoice.setOrderNo("OR" + LocalDate.now().getYear() + generateRandom());

		return invoice;

	}

	private String generateRandom() {
		StringBuilder random = new StringBuilder();
		for (int i = 0; i < 10; i++)
			random.append((int) ((Math.random() * 10)));
		return random.toString();
	}

	private Invoice buyProduct(Invoice invoice, CardDetail card) throws Exception {
		BigDecimal currBalance = new BigDecimal(card.getAmount()).setScale(2, RoundingMode.HALF_DOWN);
		BigDecimal invoiceBalance = new BigDecimal(invoice.getGrandTotal());
		if (currBalance.compareTo(invoiceBalance) == -1)
			throw new Exception("Not Sufficient Balance");

		currBalance = currBalance.subtract(invoiceBalance);
		log.info(currBalance.toString());
		card.setAmount(currBalance.toString());
		cardService.updateCard(card);

		invoice = invoiceRepo.save(invoice);
		return invoice;

	}
boolean error;
	private void subtractStock(List<CartProducts> products) throws OutOfStockException {
		List<Long> ids = products.stream().map(product -> product.getId()).collect(Collectors.toList());
		List<VendorProduct> vProducts = productReop.findAllById(ids);
		
		
		 vProducts= vProducts.stream().map(vProduct->{
			products.stream().forEach(cProduct->{
			
				if(vProduct.getId()==cProduct.getId()) {
					int qty=Integer.parseInt(cProduct.getQuantity());
					int stock=Integer.parseInt(vProduct.getStock());
					if(qty<=stock) {
						vProduct.setStock(String.valueOf(stock-qty));
						System.out.println("here213:"+stock+" "+qty);
					}else error=true;
				}
			});
			
						
			return vProduct;
		}).collect(Collectors.toList());
		 
		 if(error) {
			 error=false;
			 throw new OutOfStockException("Out of Stock");
		 }
		
		productReop.saveAll(vProducts);
		

	}

	public Invoice getInvoiceById(long id) {
		return invoiceRepo.findById(id).orElseThrow();
	}

	public List<Invoice> getInvoiceByCustomerEmail(String email) {
		return invoiceRepo.findAllBycEmail(email);
	}

}
