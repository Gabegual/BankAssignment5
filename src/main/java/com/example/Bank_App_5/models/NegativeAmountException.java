package com.example.Bank_App_5.models;

public class NegativeAmountException  extends Exception {
	
	NegativeAmountException(String ERROR){
		super(ERROR);
	}
}
