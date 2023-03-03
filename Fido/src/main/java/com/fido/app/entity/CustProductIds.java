package com.fido.app.entity;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class CustProductIds {
    
	@Id
	private long customerId;
	
	@ElementCollection
	private List<Long> productIds;
}
