package com.fido.app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fido.app.entity.CardDetail;
import com.fido.app.services.AuthDetail;
import com.fido.app.services.CardService;

@RestController
@RequestMapping("/api")
public class CardController {
	
	
		
	@Autowired 
	CardService cardService;
	
	@Autowired
	private AuthDetail authDetail;
	
	
	@GetMapping("/cards")
	public List<CardDetail> getCards() {
		return cardService.getAllCard();
	}
	
	@GetMapping("/card/{id}")
	public List<CardDetail> getCardByCustomerId(@PathVariable("id") long id) {
		 return cardService.getCardByCustomerId(id);
	}
	
	@GetMapping("/activeCard/{id}")
	public List<CardDetail> getActivedCardByCustomerId(@PathVariable("id") long id){
		return cardService.getCardByCustomerId(id).stream().filter(card->card.isActivate()).collect(Collectors.toList());
		
	}
	
	@GetMapping("/invertActive/{cardId}")
	public String invertCardStatus(@PathVariable("cardId") long id) {
		 
		
		return cardService.invertCardStatus(id)?"Card is active now":"Card is deactive"; 
	}
	
	@GetMapping("/card")
	public List<CardDetail> getCard() {
		
		var temp=authDetail.getCustomerDetail();
		return cardService.getCardByCustomerId(temp.getId());
	}
	
	@PostMapping("/card")
	public CardDetail addCards(@RequestBody CardDetail card) throws Exception {
		
		if(!authDetail.isAdmin())
			throw new Exception("Invild User Only For Admin");
		
		card=cardService.createCard(card.getCardType(), card.getCustomerId());

		System.out.println(card);		
		return card;
	}
	
	
	
	
	
	

}
