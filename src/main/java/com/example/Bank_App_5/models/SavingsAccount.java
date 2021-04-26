package com.example.Bank_App_5.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SavingsAccount extends BankAccount {

	public SavingsAccount() {
		super();
	}

	public SavingsAccount(double openBalance) {
		super(openBalance, 0.01);
	}

	public SavingsAccount(long accountNumber, double openingBalance, double interestRate, Date accountOpenedOn) {
		super(accountNumber, openingBalance, interestRate, accountOpenedOn);
	}

	public String toString() {
		return "Savings Account Balance: $" + balance + "\n" + "Savings Account Interest Rate: " + interestRate + "\n"
				+ "Savings Account Balance in 3 years: $" + futureValue(3);

	}

	public static SavingsAccount readFromString(String accountData) throws ParseException, NumberFormatException {
		String[] holding = accountData.split(",");
		SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
		long accountNumber = Long.parseLong(holding[0]);
		double balance = Double.parseDouble(holding[1]);
		double interestRate = Double.parseDouble(holding[2]);
		Date accountOpenedOn = date.parse(holding[3]);

		return new SavingsAccount(accountNumber, balance, interestRate, accountOpenedOn);
	}
}