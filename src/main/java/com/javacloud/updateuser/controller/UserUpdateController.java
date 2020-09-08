package com.javacloud.updateuser.controller;

import com.javacloud.updateuser.model.AccountHolder;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
@RequestMapping("/bank/user/")
public class UserUpdateController {
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/update")
    @HystrixCommand(fallbackMethod = "reliable")
    ResponseEntity<AccountHolder> updateDetails(@RequestBody AccountHolder accountHolder) {
        try{
//            AccountHolder account = repository.findByUserName(accountHolder.getUserName());
//            if(ObjectUtils.isEmpty(account))
//                return null; // throw exception instead
            URI uri = URI.create("http://localhost:8081/bank/update");
            if ( this.restTemplate.getInterceptors().size()<1)
                    this.restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("user", "password"));
            AccountHolder account = this.restTemplate.postForObject(uri, accountHolder, AccountHolder.class);
            return new ResponseEntity(account, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }

    public ResponseEntity<AccountHolder> reliable(AccountHolder accountHolder) {
        return  new ResponseEntity(new AccountHolder(), HttpStatus.OK);
    }

}
