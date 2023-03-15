 package com.fido.app.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fido.app.entity.CardDetail;
import com.fido.app.entity.DebitHistory;
import com.fido.app.exception.CardExpireException;
import com.fido.app.exception.InvalidException;
import com.fido.app.model.Response;
import com.fido.app.services.AuthDetail;
import com.fido.app.services.CardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	public String invertCardStatus(@PathVariable("cardId") long id) throws CardExpireException {
		 
		return cardService.invertCardStatus(id)?"Card is active now":"Card is deactive"; 
	}
	
	@GetMapping("/card")
	public List<CardDetail> getCard() {
		
		var temp=authDetail.getCustomerDetail();
		return cardService.getCardByCustomerId(temp.getId());
	}
	
	@PostMapping("/card")
	public CardDetail addCards(@Valid @RequestBody  CardDetail card) throws Exception {
		
		if(!authDetail.isAdmin())
			throw new InvalidException("Invild User Only For Admin");
		log.info(card.toString());
		card=cardService.createCard(card.getCardType(), card.getCustomerId());

		log.info(card.toString());		
		return card;
	}
	
	@PatchMapping("/cardRecharge/{cardId}")
	public ResponseEntity<Response> getReacharge(@PathVariable("cardId") long id) throws Exception{
//		if(!authDetail.isAdmin()) throw new InvalidException("Contact to Admin to reacharge");
//		if(id==null) throw new Exception("Id is not set");
		cardService.getRecharged(id);
		 
		 return ResponseEntity.status(HttpStatus.OK).body(new Response("200","Reacharged"));
		 
		 
	}
	
	@GetMapping("/cardTransation/{cid}")
	public List<DebitHistory> getCardTransationalHistoryByCustomerId(@PathVariable("cid") long id ){
		 return cardService.getTransactionalHistory(id);
	}
	
	@GetMapping("/cardTransaction")
	public List<DebitHistory> getCardTransactionalHistory(){
		
		var temp=authDetail.getCustomerDetail();
		return cardService.getTransactionalHistory(temp.getId());
	}
	
	
	
	

}
