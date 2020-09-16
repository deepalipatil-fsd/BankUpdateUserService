package com.javacloud.updateuser.model;

import com.javacloud.updateuser.UpdateuserApplication;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = UpdateuserApplication.class)
class BankUserTest {
	BankUser user;
	@Test
	public void testBankUserDetails() {
		AccountHolder account = new AccountHolder();
		account.setUserName("user");
		account.setPassword("AIDTAIiCazaQavILI07rtA==");
		user = new BankUser(account);
		user.setAccountHolder(account);
		Assert.assertNotNull(user.getAuthorities());
		Assert.assertNotNull(user.getAccountHolder());
		Assert.assertEquals("user", user.getUsername());
		Assert.assertEquals("password", user.getPassword());
		Assert.assertTrue(user.isAccountNonExpired());
		Assert.assertTrue(user.isAccountNonLocked());
		Assert.assertTrue(user.isCredentialsNonExpired());
		Assert.assertTrue(user.isEnabled());
	}

}
