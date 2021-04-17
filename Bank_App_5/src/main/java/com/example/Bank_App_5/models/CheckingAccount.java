package com.example.Bank_App_5.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class CheckingAccount extends BankAccount {

	// public static final double INTEREST_RATE = 0.0001;
	public CheckingAccount() {
		super();
	}

	public CheckingAccount(double openBalance) {
		super(openBalance, 0.01);
	}

	public CheckingAccount(long accountNumber, double openingBalance, double interestRate, Date accountOpenedOn) {
		super(accountNumber, openingBalance, interestRate, accountOpenedOn);
	}

	public String toString() {
		return "Checking Account Balance: $" + getBalance() + "\n" + "Checking Account Interest Rate: "
				+ getInterestRate() + "\n" + "Checking Account Balance in 3 years: $" + futureValue(3);

	}

	public static CheckingAccount readFromString(String accountData) throws ParseException {

		String[] holding = accountData.split(",");
		SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
		// [0] is accountNumber, [1] is balance, [2] is interestRate, date is [3] which
		// is SimpleDate
		long accountNumber = Long.parseLong(holding[0]);
		double balance = Double.parseDouble(holding[1]);
		double interestRate = Double.parseDouble(holding[2]);
		Date accountOpenedOn = date.parse(holding[3]);
		CheckingAccount newCheckingAccount = new CheckingAccount(accountNumber, balance, interestRate, accountOpenedOn);
		return newCheckingAccount;
	}
}