package com.javacloud.updateuser.service;

import com.javacloud.updateuser.UpdateuserApplication;
import com.javacloud.updateuser.model.AccountHolder;
import com.javacloud.updateuser.model.LoanDetails;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = UpdateuserApplication.class)
public class BankUserDetailsServiceTest {

    @InjectMocks
    BankUserDetailsService service;
    @Mock
    private RestTemplate restTemplate;

    @Test
    public void testUserDetailsService() {
        AccountHolder account = new AccountHolder();
        assertThrows(UsernameNotFoundException.class, () -> {
            service.loadUserByUsername("user");
        });
        when(restTemplate.getForObject(anyString(), any())).thenReturn(account);
        Assert.assertNotNull(service.loadUserByUsername("user"));
    }
    @Test
    public void testGetAccountException() {
        when(restTemplate.getForObject(anyString(), any())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> {
            service.getAccount("user");
        });
    }
    @Test
    public void testGetAccount() {
        AccountHolder account = new AccountHolder();
        when(restTemplate.getForObject(anyString(), any())).thenReturn(account);
        Assert.assertNotNull(service.getAccount("user"));
    }
    @Test
    public void testLetLoanDetailsException() {
        when(restTemplate.getForObject(anyString(), any())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> {
            service.getLoanDetails("user");
        });
    }
    @Test
    public void testGetLoanDetails() {
        List<LoanDetails> loanDetailsList = new ArrayList<>();
        loanDetailsList.add(new LoanDetails());
        when(restTemplate.getForObject(anyString(), any())).thenReturn(loanDetailsList);
        Assert.assertNotNull(service.getLoanDetails("user"));
    }
    @Test
    public void testUpdateAccount() {
        AccountHolder account = new AccountHolder();
        when(restTemplate.postForObject(any(URI.class), any(), any())).thenReturn(account);
        ResponseEntity<String> response = service.updateAccount(new AccountHolder());
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody(), "Update successful");
    }
    @Test
    public void testUpdateAccountException() {
        when(restTemplate.postForObject(any(URI.class), any(), any())).thenThrow(new RuntimeException());
        ResponseEntity<String> response = service.updateAccount(new AccountHolder());
        Assert.assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        Assert.assertEquals(response.getBody(), "Update failed");
    }
    @Test
    public void testApplyForLoan() {
        LoanDetails loanDetails = new LoanDetails();
        when(restTemplate.postForObject(any(URI.class), any(), any())).thenReturn(loanDetails);
        ResponseEntity<LoanDetails> response = service.applyForLoan(new LoanDetails());
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody(), "Applied for loan successfully");
    }
    @Test
    public void testApplyForLoanException() {
        when(restTemplate.postForObject(any(URI.class), any(), any())).thenThrow(new RuntimeException());
        ResponseEntity<LoanDetails> response = service.applyForLoan(new LoanDetails());
        Assert.assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        Assert.assertEquals(response.getBody(), "Loan application failed");
    }
    @Test
    public void testRegisterUser() {
        AccountHolder account = new AccountHolder();
        when(restTemplate.postForObject(any(URI.class), any(), any())).thenReturn(account);
        ResponseEntity<AccountHolder> response = service.registerUser(account);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody(), account);
    }
    @Test
    public void testRegisterUserException() {
        when(restTemplate.postForObject(any(URI.class), any(), any())).thenThrow(new RuntimeException());
        ResponseEntity<AccountHolder> response = service.registerUser(new AccountHolder());
        Assert.assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        Assert.assertNull(response.getBody());
    }

    @Test
    public void testLoginException() {
        when(restTemplate.getForObject(anyString(), any())).thenThrow(new RuntimeException("Authentication Failed."));
        Assert.assertNull(service.login("myUser"));
    }

}
