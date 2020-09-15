package com.javacloud.updateuser.controller;

import com.javacloud.updateuser.model.AccountHolder;
import com.javacloud.updateuser.model.LoanDetails;
import com.javacloud.updateuser.service.BankUserDetailsService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/bank/user/")
public class UserUpdateController {
    @Autowired
    private BankUserDetailsService bankUserDetailsService;

    @PostMapping("/update")
    @HystrixCommand(fallbackMethod = "reliable", threadPoolProperties = {
            @HystrixProperty(name="coreSize", value = "99")})
    ResponseEntity<String> updateDetails(@RequestBody AccountHolder accountHolder) {
        return bankUserDetailsService.updateAccount(accountHolder);
    }

    @PostMapping("/register")
    ResponseEntity<AccountHolder> resisterUser(@RequestBody AccountHolder accountHolder) {
        return bankUserDetailsService.registerUser(accountHolder);
    }

    public ResponseEntity<String> reliable(AccountHolder exception) {
        System.out.println("real exception : {}"+ exception.getAccountNumber());
        return  new ResponseEntity("Service unavailable!!", HttpStatus.FORBIDDEN);
    }

    @GetMapping("/{accountNumber}")
    ResponseEntity<AccountHolder> getAccount(@PathVariable("accountNumber") String accountNumber) {
        AccountHolder accountHolder = bankUserDetailsService.getAccount(accountNumber);
        if(ObjectUtils.isEmpty(accountHolder))
            return new ResponseEntity("Account details not found", HttpStatus.BAD_REQUEST);
        return new ResponseEntity(accountHolder, HttpStatus.OK);
    }
    @PostMapping("/loan/apply")
    ResponseEntity<LoanDetails> applyForLoan(@RequestBody LoanDetails loanDetails) {
       return bankUserDetailsService.applyForLoan(loanDetails);
    }

    @GetMapping("/loan/{accountNumber}")
    ResponseEntity<List<LoanDetails>> getLoanDetails(@PathVariable("accountNumber") String accountNumber) {
        List<LoanDetails> loanDetails = bankUserDetailsService.getLoanDetails(accountNumber);
        if(ObjectUtils.isEmpty(loanDetails))
            return new ResponseEntity("Loan details not found", HttpStatus.BAD_REQUEST);
        return new ResponseEntity(loanDetails, HttpStatus.OK);
    }
}
