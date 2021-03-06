package com.javacloud.updateuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableCircuitBreaker
@SpringBootApplication
@EnableEurekaClient
public class UpdateuserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UpdateuserApplication.class, args);
		//System.out.println(EncryptionUtil.encrypt("password"));
	}

	@Bean
	@LoadBalanced
	public RestTemplate rest(RestTemplateBuilder builder) {
		return builder.build();
	}

}
