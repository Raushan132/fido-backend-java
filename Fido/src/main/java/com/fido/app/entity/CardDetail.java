package com.fido.app.entity;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Data
public class CardDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name="native", strategy = "native")
	private long id;
	private String cardNo;
	private String cvv;
	private String expDate;
	private boolean activate;
	private long customerId;
	 

}
