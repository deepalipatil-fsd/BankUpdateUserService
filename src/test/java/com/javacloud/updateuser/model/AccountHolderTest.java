package com.javacloud.updateuser.model;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class AccountHolderTest {
	private AccountHolder accountHolder = new AccountHolder();
	@Test
	void testUpdateUserApplication() {
		Date dob = new Date();
		accountHolder = new AccountHolder(12L, "123456", "name", "userName", "password", "address", "state", "country", "email@myemail.com", "ASEFT9874J", "09878676543", dob, "Savings");
		accountHolder.setAccountNumber("01234567890");
		accountHolder.setAccountType("Current");
		accountHolder.setAddress("Jaysingpur");
		accountHolder.setContactNo("01234567890");
		accountHolder.setCountry("India");
		accountHolder.setEmail("deepali@cts.com");
		accountHolder.setId(12L);
		accountHolder.setName("Indiana");
		accountHolder.setPan("ASDFG0987G");
		accountHolder.setPassword("password");
		accountHolder.setState("Maharashtra");
		accountHolder.setUserName("user");
		accountHolder.setDob(dob);
		Assert.assertEquals(accountHolder.getAccountNumber(),"01234567890");
		Assert.assertEquals(accountHolder.getAccountType(),"Current");
		Assert.assertEquals(accountHolder.getAddress(),"Jaysingpur");
		Assert.assertEquals(accountHolder.getContactNo(),"01234567890");
		Assert.assertEquals(accountHolder.getCountry(),"India");
		Assert.assertEquals(accountHolder.getEmail(),"deepali@cts.com");
		Assert.assertNotNull(accountHolder.getId());
		Assert.assertEquals(accountHolder.getName(),"Indiana");
		Assert.assertEquals(accountHolder.getPan(),"ASDFG0987G");
		Assert.assertEquals(accountHolder.getPassword(),"password");
		Assert.assertEquals(accountHolder.getState(),"Maharashtra");
		Assert.assertEquals(accountHolder.getUserName(),"user");
		Assert.assertEquals(accountHolder.getDob(), dob);

	}

}
