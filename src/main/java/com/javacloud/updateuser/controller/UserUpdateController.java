package com.javacloud.updateuser.controller;

import com.javacloud.updateuser.model.AccountHolder;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
@RequestMapping("/bank/user/")
public class UserUpdateController {
    @Autowired
    private RestTemplate restTemplate;
    BasicAuthenticationInterceptor authInterceptor = new BasicAuthenticationInterceptor("user", "password");

    @PostMapping("/update")
    @HystrixCommand(fallbackMethod = "reliable", threadPoolProperties = {
            @HystrixProperty(name="coreSize", value = "99")})
    ResponseEntity<String> updateDetails(@RequestBody AccountHolder accountHolder) {
        try{
            URI uri = URI.create("http://bank-registration-service/bank/update");
            if ( !restTemplate.getInterceptors().contains(authInterceptor))
                restTemplate.getInterceptors().add(authInterceptor);
            restTemplate.postForObject(uri, accountHolder, AccountHolder.class);
            return new ResponseEntity("Update successful", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("Update failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> reliable(AccountHolder exception) {
        System.out.println("real exception : {}"+ exception.getAccountNumber());
        return  new ResponseEntity("Service unavailable!!", HttpStatus.FORBIDDEN);
    }
    @GetMapping("/{accountNumber}")
    AccountHolder getAccount(@PathVariable("accountNumber") String accountNumber) {
        if ( !restTemplate.getInterceptors().contains(authInterceptor))
            restTemplate.getInterceptors().add(authInterceptor);
        return restTemplate.getForObject("http://bank-registration-service/bank/accountdetails/"+accountNumber, AccountHolder.class);
    }
}
