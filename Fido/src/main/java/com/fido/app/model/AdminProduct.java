package com.fido.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fido.app.entity.VendorProduct;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class AdminProduct extends VendorProduct {
	
	@JsonProperty(value = "v_name")
	private String vName;
	
	public void setProduct(VendorProduct product) {
		this.setBrand(product.getBrand());
		this.setCategory(product.getCategory());
		this.setCreatedAt(product.getCreatedAt());
		this.setCreatedBy(product.getCreatedBy());
		this.setDescription(product.getDescription());
		this.setDiscount(product.getDiscount());
		this.setGst(product.getGst());
		this.setId(product.getId());
		this.setPrice(product.getPrice());
		this.setProuductOwnerId(product.getProuductOwnerId());
		this.setRating(product.getRating());
		this.setStock(product.getStock());
		this.setThumbnail(product.getThumbnail());
		this.setTitle(product.getTitle());
		this.setUpdateAt(product.getUpdateAt());
		this.setUpdateBy(product.getUpdateBy());
	}

}
