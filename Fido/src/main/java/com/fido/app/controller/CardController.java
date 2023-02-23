package com.fido.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fido.app.entity.CardDetail;

@RestController
@RequestMapping("/api")
public class CardController {
	
	@GetMapping("/cards")
	public String getCards() {
		return "All Cards";
	}
	
	@PostMapping("/cards/{id}")
	public String addCards(@RequestBody CardDetail card) {
		System.out.println(card);
		
		return "card is added";
	}
	

}
