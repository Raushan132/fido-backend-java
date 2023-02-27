package com.fido.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fido.app.entity.CardDetail;
import com.fido.app.repository.CardRepo;

@RestController
@RequestMapping("/api")
public class CardController {
	
	@Autowired
	private CardRepo cardRepo;
	
	
	@GetMapping("/cards")
	public String getCards() {
		return "All Cards";
	}
	
	@PostMapping("/cards")
	public String addCards(@RequestBody CardDetail card) {
		 
		cardRepo.save(card);
				
		return "card is added";
	}
	
	
	
	

}
