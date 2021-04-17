package com.example.Bank_App_5.Controllers;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Bank_App_5.models.*;
import ch.qos.logback.classic.Logger;

@RestController
public class CentralController {
	
	Logger logger = LoggerFactory.getLogger(CentralController.class);
	
	@GetMapping(value = "/")
	public String welcome() {
		return "Welcome to Merit Bank";
	}
	
	@PostMapping(value = "/CDOfferings")
	public CDOffering addCDOfferings(@RequestBody CDOffering offering) throws MissingFieldException {
		
		if(offering.getInterestRate() <= 0 || offering.getTerm() <= 0 || offering.getInterestRate() >=1) {
			logger.warn("Missing interest rate or term");
			throw new MissingFieldException("Missing interest rate or term");
		}
		
		MeritBank.addCDOffering(offering);
		return offering;
	}
	public CDOffering[] getCDOffering() throws NotFoundException {
		CDOffering[] cdOfferings = MeritBank.getCDOfferings();
		return cdOfferings;
	}

}
