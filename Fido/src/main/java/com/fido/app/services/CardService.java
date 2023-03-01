package com.fido.app.services;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fido.app.entity.CardDetail;
import com.fido.app.repository.CardRepo;

@Transactional
@Service
public class CardService {
	
	@Autowired
	private CardRepo cardRepo;

	public CardDetail createCard(String cardType, long customerId) throws Exception {
		
		System.out.println(cardType+":"+cardType.equalsIgnoreCase("GOLD"));
	
		if(this.isCardExist(customerId, cardType)) throw new Exception(cardType+" Card is already exist");
		
		int month=LocalDate.now().getMonthValue();
		
		String expDate= (month<10?"0":"")+month +"/"+ LocalDate.now().plusYears(1).getYear();
		CardDetail card = new CardDetail();
		
		card.setCardNo(getCardNo(cardType));
		card.setCardType(cardType);
		card.setCustomerId(customerId);
		card.setExpDate(expDate);
		card.setCvv(generateCvv());
		card.setAmount(getAmount(cardType));
//		card=cardRepo.save(card);
		
		return card;
		
	}
	
	public List<CardDetail> getCardByCustomerId(long id) {
		   
		   return cardRepo.findByCustomerId(id);
	}
	
	public boolean invertCardStatus(long id) {
		  var temp=  cardRepo.findById(id).orElseThrow();
		  temp.setActivate(!temp.isActivate());
		  return temp.isActivate();
	}
	
	private boolean isCardExist(long id,String cardType){
		
		System.out.println("i am here 321 "+id);
		int year=LocalDate.now().getYear();
		int month=LocalDate.now().getMonthValue();
		
		var cards= cardRepo.findByCustomerId(id);
		Predicate<CardDetail> isCardTypePersent= (card)->card.getCardType().equalsIgnoreCase(cardType);
		Predicate<CardDetail> isNotExpire=(card)->{
			String[] date=card.getExpDate().split("[/]");
			
			return !((Integer.parseInt(date[1]) <= year) &&   (Integer.parseInt(date[0])<month));
			
			};
//			System.out.println("isCardTypePersent:"+isCardTypePersent+" isNotExpire:"+isNotExpire);
		   return cards.stream().anyMatch(isCardTypePersent.and(isNotExpire));
	   
	     
	}
	
	private String generateCvv() {
		 String random="";
		 for(int i=0;i<3;i++)
		   random+=(int)(Math.random()*10);
		return random;
	}
	
	private String getAmount(String cardType) throws Exception {
		String amt="0";
		
		switch (cardType) {
		case "GOLD": amt="10000";
			break;
		case "PLATINUM": amt="15000";
			break;
		case "DIAMOND": amt="25000";
			break;
		default:
			throw new Exception("Invalid Card Selection");
		}
		return amt;
	}
	
	private String getCardNo(String cardType) throws Exception {
		String accountNo="";
		int year= LocalDate.now().getYear();
		StringBuilder random= new StringBuilder();
		for(int i=0;i<8 ;i++)
					random.append((int)(Math.random()*10));
		switch (cardType.toUpperCase()) {
		case "GOLD": accountNo="2345";
			break;
		case "PLATINUM": accountNo="3345";
			break;
		case "DIAMOND": accountNo="5345";
			break;
		default:
			throw new Exception("Invalid Card Selection");
		}
		accountNo+=year+random.toString();
		return accountNo;
	}
}
