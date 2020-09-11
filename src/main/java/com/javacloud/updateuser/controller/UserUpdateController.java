package com.javacloud.updateuser.controller;

import com.javacloud.updateuser.model.AccountHolder;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
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
    @HystrixCommand(fallbackMethod = "reliable", threadPoolProperties = {
            @HystrixProperty(name="coreSize", value = "99")})
    ResponseEntity<String> updateDetails(@RequestBody AccountHolder accountHolder) {
        try{
//            AccountHolder account = repository.findByUserName(accountHolder.getUserName());
//            if(ObjectUtils.isEmpty(account))
//                return null; // throw exception instead
            URI uri = URI.create("http://localhost:8081/bank/update");
            if ( restTemplate.getInterceptors().size()<1)
                    restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("user", "password"));
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

}
