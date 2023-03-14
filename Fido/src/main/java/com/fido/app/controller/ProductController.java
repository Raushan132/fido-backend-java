package com.fido.app.controller;

import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fido.app.entity.VendorProduct;
import com.fido.app.model.AdminProduct;
import com.fido.app.model.Response;
import com.fido.app.repository.VendorProductReop;
import com.fido.app.services.AdminProductService;
import com.fido.app.services.AuthDetail;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * Product Controller</br>
 * add the product by vendor and ADMIN</br>
 * get all the product by vendor and ADMIN</br>
 * get specific product detail by id and email</br>
 * delete the product by id
 * </p>
 * 
 * @author LENOVO
 *
 */
@Slf4j
@Validated
@RestController
public class ProductController {

	@Autowired
	private VendorProductReop productReop;

	@Autowired
	private AuthDetail authDetail;
	
	@Autowired
	private AdminProductService productService;

	@GetMapping(value = "/vendor")
	public List<AdminProduct> getVendor( ) {
        var product=productService.getProductWithName();
		return product;
	}

	@PutMapping(value = "/setProduct")
	public ResponseEntity<Response> setProduct(@Valid @RequestBody VendorProduct product) throws Exception {
		log.info(product.toString());
		var temp = authDetail.getVendorDetail();
		product.setProuductOwnerId(temp.getId());
		productReop.save(product);

		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("201", "Product is Stored"));

	}

	@GetMapping(value = "/getProduct")
	public List<AdminProduct> getProduct() {

		return productService.getProductWithName();
	}

	@GetMapping("/product/{id}")
	public List<VendorProduct> getProductByVendorId(@PathVariable("id") long id) {
		return productReop.findAllByProuductOwnerId(id);
	}

	@GetMapping("/vendorProducts")
	public List<VendorProduct> getProductByVendor() throws Exception {

		var temp = authDetail.getVendorDetail();
		return productReop.findAllByProuductOwnerId(temp.getId());
	}

	@GetMapping(value = "/getProductDetails/{id}")
	public VendorProduct getProductById(@PathVariable("id") long id) {

		return productReop.findById(id).orElseThrow();
	}

	@DeleteMapping(value = "/getDeleteProduct/{id}")
	public boolean getDeleteProduct(@PathVariable("id") long id) {

		if (!authDetail.isAdmin())
			return false;

		productReop.deleteById(id);
		return true;
	}

}
