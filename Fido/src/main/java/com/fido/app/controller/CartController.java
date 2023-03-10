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
			List<CustProductIds> productIds = cartIdsRepo.getAllProductIdsByCustomerIds(customerId);

			Function<CustProductIds, VendorProduct> fun = (cpIds) -> {

				return vendorProductReop.findById(cpIds.getProductIds()).orElseThrow();
			};
			
			return productIds.stream().map(fun).collect(Collectors.toList());
			
		

		   
		} catch (NoSuchElementException e) {

			throw new Exception("Customer/Product is not Found in CartConroller at Get/ cart/{id}...");
		}

	}
	
	
	
	@DeleteMapping("/cart")
	public boolean deleteProductId(@RequestBody Cart cart) {
		System.out.println("cart:"+cart);
		List<Long> pids=extractCart.extractIdsFormProudcts(cart.getVendorProducts());
		pids.stream().forEach(pid->cartIdsRepo.deleteByCustomerIdsAndProductIds(cart.getCustomerId(), pid));
		
		return true;
	}
	
	@DeleteMapping("/cart/{cid}")
	public boolean deleteProductByCustomerId(@PathVariable long cid) {
		cartIdsRepo.deleteById(cid);
		return true;
	}
	


	@PostMapping("/cart")
	public long acceptProduct(@RequestBody Cart cart) {
		System.out.println(cart);
		
		List<Long> pids=extractCart.extractIdsFormProudcts(cart.getVendorProducts());
		System.out.println(pids);
		pids.stream().forEach(pid->{		
			  CustProductIds cpIds =  new CustProductIds();
			    cpIds.setCustomerIds(cart.getCustomerId());
			    cpIds.setProductIds(pid);
			    cartIdsRepo.save(cpIds);
			});
		return countOfProductInCart(cart.getCustomerId());
	}
	
	@GetMapping("/countCart")
	public long countOfProductInCart(long customerIds) {
		 return cartIdsRepo.countByCustomerIds(customerIds);
	}
}
