package com.example.Bank_App_5.models;

public class DepositTransaction extends Transaction {
	
	DepositTransaction(BankAccount targetAccount, double amount){
		this.targetAccount = targetAccount;
		this.amount = amount;
	}

	@Override
	public void process()
			throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException {
		
		if(amount < 0) {
			
			throw new NegativeAmountException("ERROR! AMOUNT IS NEGATIVE CAN NOT TRANSFER !");
		}
		else if (amount > 1000) {
			
			throw new ExceedsFraudSuspicionLimitException("ERROR TRANSACTION CAN NOT BE COMPLETED ");
		}
		
		else {
			
			System.out.println("Transaction amount: ");
			
	
			targetAccount.deposit(amount);
		}
		
	}
		
}

