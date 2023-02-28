package com.fido.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fido.app.entity.CardDetail;
import com.fido.app.repository.CustomerRepo;
import com.fido.app.services.CardService;

@RestController
@RequestMapping("/api")
public class CardController {
	
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired 
	CardService cardService;
	
	
	@GetMapping("/cards")
	public String getCards() {
		return "All Cards";
	}
	
	@GetMapping("/card/{id}")
	public List<CardDetail> getCardByCustomerId(@PathVariable("id") long id) {
		 return cardService.getCardByCustomerId(id);
	}
	
	@GetMapping("/invertActive/{cardId}")
	public String invertCardStatus(@PathVariable("cardId") long id) {
		 
		
		return cardService.invertCardStatus(id)?"Card is active now":"Card is deactive"; 
	}
	
	@GetMapping("/card")
	public List<CardDetail> getCard() {
		String email= SecurityContextHolder.getContext().getAuthentication().getName();
		var temp=customerRepo.findByEmail(email).orElseThrow();
		return cardService.getCardByCustomerId(temp.getId());
	}
	
	@PostMapping("/card")
	public CardDetail addCards(@RequestBody CardDetail card) throws Exception {
		
		card=cardService.createCard(card.getCardType(), card.getCustomerId());

		System.out.println(card);		
		return card;
	}
	
	
	
	

}
