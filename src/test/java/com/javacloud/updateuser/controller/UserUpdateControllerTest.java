package com.javacloud.updateuser.controller;

import com.javacloud.updateuser.UpdateuserApplication;
import com.javacloud.updateuser.model.AccountHolder;
import com.javacloud.updateuser.model.LoanDetails;
import com.javacloud.updateuser.service.BankUserDetailsService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = UpdateuserApplication.class)
public class UserUpdateControllerTest {

    @InjectMocks
    UserUpdateController controller;
    @Mock
    private BankUserDetailsService bankUserDetailsService;

    @Test
    public void testUpdateUserDetails() {
        when(bankUserDetailsService.updateAccount(any(AccountHolder.class))).thenReturn(new ResponseEntity("Updated", HttpStatus.OK));
        ResponseEntity<String> response = controller.updateDetails(new AccountHolder());
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
    @Test
    public void testResisterUser() {
    	AccountHolder account = new AccountHolder();
        when(bankUserDetailsService.registerUser(any(AccountHolder.class))).thenReturn(new ResponseEntity(account, HttpStatus.OK));
        ResponseEntity<AccountHolder> response = controller.resisterUser(new AccountHolder());
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody(), account);
    }
    @Test
    public void testGetAccount() {
    	AccountHolder account = new AccountHolder();
        when(bankUserDetailsService.getAccount("1234567890")).thenReturn(account);
        ResponseEntity<AccountHolder> response = controller.getAccount("1234567890");
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody(), account);
		response = controller.getAccount("invalidAccount");
		Assert.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		Assert.assertEquals(response.getBody(), "Account details not found");
	}
    @Test
    public void testApplyForLoan() {
    	LoanDetails loanDetails = new LoanDetails();
    	loanDetails.setAccountNumber("1234567890");
        when(bankUserDetailsService.getAccount("1234567890")).thenReturn(new AccountHolder());
        when(bankUserDetailsService.applyForLoan(loanDetails)).thenReturn(new ResponseEntity(loanDetails, HttpStatus.OK));
        ResponseEntity<LoanDetails> response = controller.applyForLoan(loanDetails);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody(), loanDetails);
		response = controller.applyForLoan(new LoanDetails());
		Assert.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		Assert.assertEquals(response.getBody(), "Account not available hence cant apply for loan.");
	}
    @Test
    public void testGetLoanDetails() {
    	List<LoanDetails> loanDetails = new ArrayList<>();
        loanDetails.add(new LoanDetails());
        when(bankUserDetailsService.getLoanDetails("1234567890")).thenReturn(loanDetails);
        ResponseEntity<List<LoanDetails>> response = controller.getLoanDetails("1234567890");
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody(), loanDetails);
        when(bankUserDetailsService.getLoanDetails("invalidAccNum")).thenReturn(new ArrayList<>());
        response = controller.getLoanDetails("invalidAccNum");
		Assert.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		Assert.assertEquals(response.getBody(), "Loan details not found.");
	}

    @Test
    public void testReliableInvoke() {
        ResponseEntity<String> response = controller.reliable(new AccountHolder());
        Assert.assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }
}
