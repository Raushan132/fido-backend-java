package com.fido.app.controller;

import java.util.NoSuchElementException;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fido.app.exception.CardExistedException;
import com.fido.app.exception.CardExpireException;
import com.fido.app.exception.InvalidException;
import com.fido.app.exception.InvalidRequest;
import com.fido.app.exception.NotSufficientBalanceException;
import com.fido.app.exception.OutOfStockException;
import com.fido.app.model.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(annotations=RestController.class)
@Order(value = 1)
public class ExceptionController {
	
	
	@ExceptionHandler({InvalidException.class})
	public ResponseEntity<Response> InvalidExceptionHandler(Exception exception){
		 Response response= new Response("401",exception.getMessage());
		 log.info("User not found"+exception.getMessage());
		 return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
	}
	
	
	@ExceptionHandler({UsernameNotFoundException.class,NoSuchElementException.class})
	public ResponseEntity<Response> UserNotFoundExceptionHandler(Exception exception){
		 Response response= new Response("404",exception.getMessage());
		 log.info("User not found"+exception.getMessage());
		 return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}
	

	
	@ExceptionHandler({MethodArgumentNotValidException.class,HttpMessageNotReadableException.class})
	public ResponseEntity<Response> methodArgumentNotValidExceptionHandler(Exception exception){
		 Response response= new Response("400","Invalid Data");
		 log.info(exception.getMessage());
		 return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({CardExpireException.class,CardExistedException.class,InvalidRequest.class})
	public ResponseEntity<Response> cardExpireExceptionHandler(Exception exception){
		 Response response= new Response("406",exception.getMessage());
		 log.info(exception.getMessage());
		 return new ResponseEntity<>(response,HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler({OutOfStockException.class,NotSufficientBalanceException.class})
	public ResponseEntity<Response> OutOfStockExceptionHandler(Exception exception){
		 Response response= new Response("422",exception.getMessage());
		 log.info(exception.getMessage());
		 return new ResponseEntity<>(response,HttpStatus.UNPROCESSABLE_ENTITY);
	}


	
	
	
	@ExceptionHandler({Exception.class})
	public ResponseEntity<Response> exceptionHandler(Exception exception){
		 Response response= new Response("500",exception.getMessage());
		 log.info(exception.getMessage());
		 return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
