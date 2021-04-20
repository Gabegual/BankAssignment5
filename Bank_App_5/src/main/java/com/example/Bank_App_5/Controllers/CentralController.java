package com.example.Bank_App_5.Controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Bank_App_5.models.ExceedsCombinedBalanceLimitException;
import com.example.Bank_App_5.exceptions.MissingFieldException;
import com.example.Bank_App_5.models.NegativeAmountException;
import com.example.Bank_App_5.exceptions.NotFoundException;
import com.example.Bank_App_5.models.AccountHolder;
import com.example.Bank_App_5.models.CDAccount;
import com.example.Bank_App_5.models.CDAccountDTO;
import com.example.Bank_App_5.models.CDOffering;
import com.example.Bank_App_5.models.CheckingAccount;
import com.example.Bank_App_5.models.MeritBank;
import com.example.Bank_App_5.models.SavingsAccount;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController
public class CentralController {
	
	/*@GetMapping(value="/greeting")
	public String greeting() {
		return "Hello";
	}*/
	@PostMapping(value="/CDOfferings")
	@ResponseStatus(HttpStatus.CREATED)
	public CDOffering cdOfferings(@RequestBody CDOffering cdoffering) throws MissingFieldException {
		
		if((cdoffering.getTerm()==0)||cdoffering.getInterestRate()<=0||cdoffering.getTerm()<1||cdoffering.getInterestRate()>=1){
			throw new MissingFieldException("Invalid details");
		}
		
		MeritBank.addCDOffering(cdoffering);
		return cdoffering;
	}
	
	@GetMapping(value="/CDOfferings")
	public List<CDOffering> getCDOfferings() {
		
		return MeritBank.getCDOffering();
	}
	
	@PostMapping(value="/AccountHolders")
	@ResponseStatus(HttpStatus.CREATED)
	public AccountHolder addAccHolders(@RequestBody @Valid AccountHolder accountHolder) throws MissingFieldException {
		if ((accountHolder.getFirstName() == null) || (accountHolder.getLastName() == null) ||(accountHolder.getSSN() == null)) {
			throw new MissingFieldException("Invalid details");
		}
		MeritBank.addAccountHolder(accountHolder);
		return accountHolder;
	}
	
	@GetMapping(value="/AccountHolders")
	public AccountHolder[] getAccHolders() {
		return MeritBank.getAccountHolders();
	}
	
	@GetMapping(value="/AccountHolders/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public AccountHolder getAccHoldersById(@PathVariable int id, AccountHolder accountHolder) throws NotFoundException{
		if (id<=MeritBank.accHolderList.size()) {
			if(accountHolder.getId()==id) {
				return MeritBank.accHolderList.get(id-1);
			}
		}
		 throw new NotFoundException("Invalid id");
	} 
	
	@PostMapping(value="/AccountHolders/{id}/CheckingAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CheckingAccount addCheckingAcc(@RequestBody CheckingAccount ch, @PathVariable int id) throws NotFoundException,ExceedsCombinedBalanceLimitException,NegativeAmountException {
			
		
		if (id<=MeritBank.accHolderList.size()) {
			AccountHolder accountHolder = MeritBank.getAccountHolderById(id);
			if(ch.getBalance()<0) {
				throw new NegativeAmountException();
			} 				
			if (accountHolder.getCombinedBalance()+ch.getBalance()>250000) {
				throw new ExceedsCombinedBalanceLimitException("exceeds limit of amount 250,000 max");
			}
			ch=new CheckingAccount(MeritBank.getNextAccountNumber(),ch.getBalance(),CheckingAccount.CHECKING_INTERESTRATE,MeritBank.getDate());
			accountHolder.addCheckingAccount(ch);
			
			return ch;
		}
		throw new NotFoundException("Invalid id");
	}
	
	@GetMapping(value="/AccountHolders/{id}/CheckingAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CheckingAccount[] getCheckingAcc(AccountHolder accountHolder, @PathVariable int id) throws NotFoundException {
		if(id>MeritBank.accHolderList.size()) {
			throw new NotFoundException("Invalid id");
		}
		accountHolder = MeritBank.getAccountHolderById(id);		
		return accountHolder.getCheckingAccounts();
	}
	
	@PostMapping(value="/AccountHolders/{id}/SavingsAccounts") 
	@ResponseStatus(HttpStatus.CREATED)
	public SavingsAccount addSavingsAcc(@RequestBody SavingsAccount sv, @PathVariable int id) throws NotFoundException,ExceedsCombinedBalanceLimitException,NegativeAmountException{
		if(id<=MeritBank.accHolderList.size()) {
			AccountHolder accountHolder = MeritBank.getAccountHolderById(id);
			if(sv.getBalance()<0) {
				throw new NegativeAmountException();
			} 
			if((accountHolder.getCombinedBalance() + sv.getBalance()) > 250000) {
					throw new ExceedsCombinedBalanceLimitException("exceeds limit of amount 250,000 max");
			}
			sv=new SavingsAccount(MeritBank.getNextAccountNumber(),sv.getBalance(),SavingsAccount.SAVINGS_INTERESTRATE,MeritBank.getDate());
			accountHolder.addSavingsAccount(sv);

			return sv;
		}
		
		throw new NotFoundException("Invalid id");
	}
	
	@GetMapping(value="/AccountHolders/{id}/SavingsAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public SavingsAccount[] getSavingsAcc(AccountHolder accountHolder, @PathVariable int id) throws NotFoundException {
		if(id>MeritBank.accHolderList.size())
			throw new NotFoundException("Invalid id");
		accountHolder = MeritBank.getAccountHolderById(id);
		return accountHolder.getSavingsAccounts();
	}
	
	@PostMapping(value="/AccountHolders/{id}/CDAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CDAccount addCDAccounts(@RequestBody CDAccountDTO dto,@PathVariable int id) throws NotFoundException,NegativeAmountException {
		if(id<=MeritBank.accHolderList.size()) {
			
			AccountHolder accountHolder = MeritBank.getAccountHolderById(id);
			CDOffering cdo = MeritBank.getCDOfferingById(dto.getCdOffering().getId());
			CDAccount cda = new CDAccount(cdo, dto.getBalance());
			
			if(cda.getBalance()<0) {
				throw new NegativeAmountException();
			} 
			
			accountHolder.addCDAccount(cda); 
			return cda;
		}
		throw new NotFoundException("Invalid id");
	}
	
	@GetMapping(value="/AccountHolders/{id}/CDAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CDAccount[] getCDAcc(AccountHolder accountHolder, @PathVariable int id) throws NotFoundException {
		if(id>MeritBank.accHolderList.size())
			throw new NotFoundException("Invalid id");
		accountHolder = MeritBank.getAccountHolderById(id);
		return accountHolder.getCDAccounts();
	}
	
}