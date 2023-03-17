package com.fido.app.services;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fido.app.constant.CardType;
import com.fido.app.entity.CardDetail;
import com.fido.app.entity.DebitHistory;
import com.fido.app.exception.CardExistedException;
import com.fido.app.exception.CardExpireException;
import com.fido.app.exception.CardNotFoundException;
import com.fido.app.exception.InvalidException;
import com.fido.app.exception.InvalidRequest;
import com.fido.app.repository.CardRepo;
import com.fido.app.repository.DebitHistoryRepo;

@Transactional
@Service
public class CardService {
	
	@Autowired
	private CardRepo cardRepo;
	
	
	
	@Autowired
	private DebitHistoryRepo dhistoryRepo;
	
	public CardDetail createCard(String cardType, long customerId) throws Exception {
		
		
	
		if(this.isCardExist(customerId, cardType)) throw new CardExistedException(cardType+" Card is already exist");
		
		int month=LocalDate.now().getMonthValue();
		
		String expDate= (month<10?"0":"")+month +"/"+ LocalDate.now().plusYears(1).getYear();
		CardDetail card = new CardDetail();
		
		card.setCardNo(getCardNo(cardType));
		card.setCardType(cardType);
		card.setCustomerId(customerId);
		card.setExpDate(expDate);
		card.setCvv(generateCvv());
		card.setAmount(getAmount(cardType));
		card=cardRepo.save(card);
		
		return card;
		
	}
	
	public List<CardDetail> getAllCard(){
		return cardRepo.findAll();
	}
	public List<CardDetail> getCardByCustomerId(long id) {
		   
		   return cardRepo.findByCustomerId(id);
	}
	
	public List<CardDetail> getCardByCustomerIdAndCardType(long id,String cardType) {
		   
		   return cardRepo.findByCustomerIdAndCardType(id,cardType);
	}
	
	public CardDetail getCardByCustomerIdAndCardId(long id,long cardId) throws InvalidException {
		try {
		return cardRepo.findByCustomerIdAndId(id,cardId).orElseThrow();
		}catch(Exception e) {
			throw new InvalidException("Card is not Find");
		}
	}
	
	public boolean invertCardStatus(long id) throws CardExpireException {
		
		
		  var temp=  cardRepo.findById(id).orElseThrow();
		  if(isCardExpire(temp)) throw new CardExpireException("Card is Expired create new one");
		  temp.setActivate(!temp.isActivate());
		  return temp.isActivate();
		  
	}
	
	public void updateCard(CardDetail card) {
		cardRepo.save(card);
	}
	
	private boolean isCardExist(long id,String cardType){
		
		var cards= getCardByCustomerIdAndCardType(id,cardType);
		if(cards.isEmpty()) return false;
		Predicate<CardDetail> isCardTypePersent= (card)->card.getCardType().equalsIgnoreCase(cardType);
		Predicate<CardDetail> isNotExpire=(card)-> !isCardExpire(card);
		
		   return cards.stream().anyMatch(isCardTypePersent.and(isNotExpire));
	   
	     
	}
	
	public boolean isCardExpire(CardDetail card) {
		int year=LocalDate.now().getYear();
		int month=LocalDate.now().getMonthValue();
		String[] date=card.getExpDate().split("[/]");
	
		boolean isExpire =(Integer.parseInt(date[1]) < year) ?true: (Integer.parseInt(date[1]) == year)? (Integer.parseInt(date[0])<month):false;
		if(isExpire && card.isActivate()) {
			 card.setActivate(false);
			 cardRepo.save(card);
		}
		return isExpire;
	}
	
	private String generateCvv() {
		 String random="";
		 for(int i=0;i<3;i++)
		   random+=(int)(Math.random()*10);
		return random;
	}
	
	private String getAmount(String cardType) throws InvalidException {
		
		
		switch (cardType) {
		case "GOLD":return CardType.GOLD.getCardAmount();
			
		case "PLATINUM":return CardType.PLATINUM.getCardAmount();
			
		case "DIAMOND": return CardType.DIAMOND.getCardAmount();
			
		default:
			throw new InvalidException("Invalid Card Selection");
		}
		
	}
	
	private String getCardNo(String cardType) throws InvalidException {
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
			throw new InvalidException("Invalid Card Selection");
		}
		accountNo+=year+random.toString();
		return accountNo;
	}
	
	
	public List<DebitHistory> getTransactionalHistory(long cid) {
		 return dhistoryRepo.findAllByCustomerId(cid);
	}
	
	
	public void getRecharged(long cardId) throws Exception {
		try {
		CardDetail card=  cardRepo.findById(cardId).orElseThrow();
		if(!card.isActivate()) throw new CardExpireException("Card is Deactive");
		if(card.getAmount().equals(this.getAmount(card.getCardType()))) throw new InvalidRequest("Already renew");
		card.setAmount(this.getAmount(card.getCardType()));
		card= cardRepo.save(card);
		dhistoryRepo.save(this.setDebitHistory(card, card.getAmount(),"CREDIT"));
		}catch(NoSuchElementException e) {
			throw new CardNotFoundException("Card not exit");
		}catch(Exception e) {
			throw e;
		}
	}
	
	private  DebitHistory setDebitHistory(CardDetail card,String amt,String msg) {
		DebitHistory debit= new DebitHistory();
		debit.setCardNo(card.getCardNo());
		debit.setCustomerId(card.getCustomerId());
		debit.setCardType(card.getCardType());
		debit.setAmount(amt);
		debit.setStatus(msg);
		return debit;
	}
}
