package com.example.Bank_App_5.models;

public class ExceedsCombinedBalanceLimitException extends Exception {

	public ExceedsCombinedBalanceLimitException(String ERROR) {
		super(ERROR);
	}
}