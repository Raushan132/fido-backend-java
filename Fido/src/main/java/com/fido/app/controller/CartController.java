package com.fido.app.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fido.app.entity.CustProductIds;
import com.fido.app.entity.VendorProduct;
import com.fido.app.exception.InvalidRequest;
import com.fido.app.model.Cart;
import com.fido.app.model.Response;
import com.fido.app.repository.CartIdsRepo;
import com.fido.app.repository.VendorProductReop;
import com.fido.app.services.AuthDetail;
import com.fido.app.services.ExtractCart;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@RestController
@RequestMapping("/api")
public class CartController {

	@Autowired
	private ExtractCart extractCart;

	@Autowired
	private VendorProductReop vendorProductReop;

	@Autowired
	private CartIdsRepo cartIdsRepo;
	
	@Autowired
	private AuthDetail authDetail;

	@GetMapping("/cart/{id}")
	public Set<VendorProduct> getCartByCustomerId(@PathVariable("id") long customerId) throws Exception {

		
		    var vendor=	authDetail.getVendorDetail();
			
		    try {
			List<CustProductIds> productIds = cartIdsRepo.findAllProductIdsByCustomerIdsAndVendorIds(customerId,vendor.getId());
			
			Function<CustProductIds, VendorProduct> fun = (cpIds) -> {
				return  vendorProductReop.findById(cpIds.getProductIds()).get();
			    
			};

			var vdproduct= productIds.stream().map(fun).collect(Collectors.toSet());
            System.out.println(vdproduct);
			return vdproduct;
		} catch (NoSuchElementException e) {

			throw new NoSuchElementException("Invalid Product...");
		}

	}

	@DeleteMapping("/cart")
	public ResponseEntity<Response> deleteProductId(@RequestBody Cart cart) {
		log.info("cart:" + cart);
		List<Long> pids = extractCart.extractIdsFormProudcts(cart.getVendorProducts());
		pids.stream().forEach(pid -> cartIdsRepo.deleteByCustomerIdsAndProductIds(cart.getCustomerId(), pid));

		return new ResponseEntity<>(new Response("204", "Cart is deleted"), HttpStatus.OK);
	}

	@DeleteMapping("/cart/{cid}")
	public ResponseEntity<Response> deleteProductByCustomerId(@PathVariable long cid) {
		cartIdsRepo.deleteById(cid);

		return new ResponseEntity<>(new Response("204", "Cart is deleted"), HttpStatus.OK);
	}

	@PostMapping("/cart")
	public ResponseEntity<Response> acceptProduct(@RequestBody Cart cart) throws Exception {
		log.info(cart.toString());
        if(cart.getVendorProducts().isEmpty()) throw new InvalidRequest("Please Select Product");
		List<Long> pids = extractCart.extractIdsFormProudcts(cart.getVendorProducts());
        var vendor = authDetail.getVendorDetail();
		pids.stream().forEach(pid -> {
			CustProductIds cpIds = new CustProductIds();
			cpIds.setCustomerIds(cart.getCustomerId());
			cpIds.setProductIds(pid);
			cpIds.setVendorIds(vendor.getId());
			cartIdsRepo.save(cpIds);
		});
		return ResponseEntity.status(HttpStatus.OK).body(new Response("200","Product is added successfully"));
	}

	
	@GetMapping("/countCart/{cid}")
	public long countOfProductInCarts(@PathVariable("cid") long customerIds) {
		return countOfProductInCart(customerIds);
	}
	
	private long countOfProductInCart( long customerIds) {
		return cartIdsRepo.countDistinctProductIdsByCustomerIds(customerIds);
	}
	
	
}
