package com.javacloud.updateuser.service;

import com.javacloud.updateuser.model.AccountHolder;
import com.javacloud.updateuser.model.BankUser;
import com.javacloud.updateuser.model.LoanDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class BankUserDetailsService implements UserDetailsService {
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AccountHolder accountHolder = login(userName);
        if(ObjectUtils.isEmpty(accountHolder))
            throw new UsernameNotFoundException("User 404");
        return new BankUser(accountHolder);
    }

    public AccountHolder login(String userName) {
        try {
            return restTemplate.getForObject("http://bank-registration-service/bank/login/"+userName, AccountHolder.class);
        } catch(Exception e) {
            e.printStackTrace();
            return new AccountHolder();
        }
    }

    public AccountHolder getAccount(String accountNumber) {
        try {
            return restTemplate.getForObject("http://bank-registration-service/bank/accountdetails/"+accountNumber, AccountHolder.class);
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while getting account info.");
        }
    }

    public List<LoanDetails> getLoanDetails(String accountNumber) {
        try {
            return restTemplate.getForObject("http://loan-application-service/loan/loandetails/"+accountNumber, List.class);
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while getting account info.");
        }
    }

    public ResponseEntity<String> updateAccount(AccountHolder accountHolder) {
        try{
            URI uri = URI.create("http://bank-registration-service/bank/update");
            restTemplate.postForObject(uri, accountHolder, AccountHolder.class);
            return new ResponseEntity("Update successful", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("Update failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<LoanDetails> applyForLoan(LoanDetails loanDetails) {
        try{
            URI uri = URI.create("http://loan-application-service/loan/apply");
            restTemplate.postForObject(uri, loanDetails, LoanDetails.class);
            return new ResponseEntity("Applied for loan successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("Loan application failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<AccountHolder> registerUser(AccountHolder accountHolder) {
        try{
            URI uri = URI.create("http://bank-registration-service/bank/register");
            AccountHolder account = restTemplate.postForObject(uri, accountHolder, AccountHolder.class);
            return new ResponseEntity(account, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
