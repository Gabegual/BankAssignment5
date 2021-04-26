package com.example.Bank_App_5.models;

public class ExceedsAvailableBalanceException extends Exception {
	
	ExceedsAvailableBalanceException(String ERROR){
		super(ERROR);
	}
}
