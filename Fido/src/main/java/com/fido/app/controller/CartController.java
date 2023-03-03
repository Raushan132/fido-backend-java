package com.fido.app.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fido.app.entity.CustProductIds;
import com.fido.app.entity.VendorProduct;
import com.fido.app.model.Cart;
import com.fido.app.repository.CartIdsRepo;
import com.fido.app.repository.VendorProductReop;
import com.fido.app.services.ExtractCart;

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

	@GetMapping("/cart/{id}")
	public List<VendorProduct> getCartByCustomerId(@PathVariable("id") long customerId) throws Exception {

	
		try {
			CustProductIds productIds = cartIdsRepo.findByCustomerId(customerId).orElseThrow();

			Function<Long, VendorProduct> fun = (id) -> {

				return vendorProductReop.findById(id).orElseThrow();
			};
			
		  return	productIds.getProductIds().stream().map(fun).collect(Collectors.toList());

		   
		} catch (NoSuchElementException e) {

			throw new Exception("Customer/Product is not Found in CartConroller at Get/ cart/{id}...");
		}

	}
	
	@DeleteMapping("/cart/{pid}/{cid}")
	public boolean deleteProductId(@PathVariable("pid") long productId ,@PathVariable("cid") long customerId) {
		     cartIdsRepo.deleteByProductIdsAndCustomerId(productId,customerId);
		return true;
	}

	@PostMapping("/cart")
	public long acceptProduct(@RequestBody Cart cart) {
		System.out.println(cart);
		CustProductIds cpIds = new CustProductIds();
		cpIds.setCustomerId(cart.getCustomerId());
		cpIds.setProductIds(extractCart.extractIdsFormProudcts(cart.getVendorProducts()));
		cartIdsRepo.save(cpIds);
		return cpIds.getProductIds().size();
	}
}
