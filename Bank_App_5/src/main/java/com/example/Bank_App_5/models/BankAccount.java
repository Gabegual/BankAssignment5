package com.example.Bank_App_5.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class BankAccount {

	public double balance;
	public double interestRate;
	public long accountNumber;
	public Date accountOpenedOn = null;
	private List<Transaction> transactions = new ArrayList<Transaction>();

	/* >------------BankAccount Constructors--------------------< */
	public BankAccount() {

	}

	public BankAccount(double balance, double interestRate) {
		this.balance = balance;
		this.interestRate = interestRate;
		this.accountOpenedOn = new Date();
		this.accountNumber = MeritBank.getNextAccountNumber();
	}

	public BankAccount(double balance, double interestRate, Date accountOpenedOn) {
		this.balance = balance;
		this.interestRate = interestRate;
		this.accountOpenedOn = accountOpenedOn;
	}

// >-----------------AccountNumber----------------------<	    
	public BankAccount(long accountNumber, double balance, double interestRate, Date accountOpenedOn) {
		this.balance = balance;
		this.interestRate = interestRate;
		this.accountOpenedOn = accountOpenedOn;
		this.accountNumber = accountNumber;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public long setAccountNumber() {
		return this.accountNumber;
	}

	public double getBalance() {
		return balance;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public Date getOpenedOn() {
		return accountOpenedOn;
	}

	public boolean withdraw(double amount) {
		if (balance <= amount) {
			System.out.println("Sorry you do  not have that much in your account you have $" + balance);
			return false;
		} else {
			balance -= amount;
			System.out.println("Your new balance is $" + balance);
			return true;
		}
	}

	public boolean deposit(double amount) {
		if (0 < amount) {
			System.out.println("Deposit bank: " + amount);
			this.balance = this.balance + amount;
			return true;
		} else
			System.out.println(" more than 250000");
		return false;
	}

	public double futureValue(int years) {
		System.out.println("This is FV Debugg" + this.balance + " " + this.interestRate);
		double value = 0.00;
		double powered = Math.pow((1 + interestRate), years);
//		double fv = 100.0 * Math.pow(1 + 0.01, 3);
		value = balance * powered;
		return value;
	}

	public void addTransaction(Transaction transaction) {
		this.transactions.add(transaction);
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public String writeToString() {
		StringBuilder accountData = new StringBuilder();
		accountData.append(accountNumber).append(",");
		accountData.append(accountOpenedOn).append(",");
		accountData.append(balance).append(",");
		accountData.append(interestRate);
		return accountData.toString();
	}

}