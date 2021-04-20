package com.example.Bank_App_5.models;

public class ExceedsCombinedBalanceLimitException extends Exception {

	ExceedsCombinedBalanceLimitException(String ERROR) {
		super(ERROR);
	}
}