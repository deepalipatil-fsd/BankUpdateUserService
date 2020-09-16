package com.javacloud.updateuser.model;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class LoanDetailsTest {
	private LoanDetails loanDetails = new LoanDetails();
	@Test
	void testLoanDetails() {
		Date date = new Date();
		loanDetails = new LoanDetails(12L, "123456","Home", 123456L, 7.1, 24, date);
		loanDetails.setAccountNumber("123456");
		loanDetails.setLoanType("Home");
		loanDetails.setLoanAmount(123456L);
		loanDetails.setId(12L);
		loanDetails.setRateOfInterest(7.1);
		loanDetails.setDuration(24);
		loanDetails.setApplicationDate(date);
		Assert.assertEquals(loanDetails.getAccountNumber(),"123456");
		Assert.assertEquals(loanDetails.getLoanType(), "Home");
		Assert.assertNotNull(loanDetails.getLoanAmount());
		Assert.assertNotNull(loanDetails.getId());
		Assert.assertNotNull(loanDetails.getRateOfInterest());
		Assert.assertNotNull(loanDetails.getDuration());
		Assert.assertEquals(loanDetails.getApplicationDate(), date);
	}

}
