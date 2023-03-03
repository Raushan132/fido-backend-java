package com.fido.app.model;

import java.util.List;

import com.fido.app.entity.VendorProduct;

import lombok.Data;

@Data
public class Cart {
   private long customerId;
   private List<VendorProduct> vendorProducts;
}
