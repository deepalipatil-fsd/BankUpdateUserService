package com.javacloud.updateuser.controller;

import com.javacloud.updateuser.UpdateuserApplication;
import com.javacloud.updateuser.model.AccountHolder;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = UpdateuserApplication.class)
public class UserUpdateControllerTest {

	@InjectMocks
	UserUpdateController controller;
	@Mock
	private RestTemplate restTemplate;
	@Test
	public void testUpdateUserDetails() {
		when(restTemplate.getInterceptors()).thenReturn(new ArrayList<>());
		ResponseEntity<String> response = controller.updateDetails(new AccountHolder());
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	@Test
	public void testExceptionOnUpdateUserDetails() {
		when(restTemplate.getInterceptors()).thenReturn(new ArrayList<>());
		when(restTemplate.postForObject(any(URI.class), any(), any())).thenThrow(new RuntimeException());
		ResponseEntity<String> response = controller.updateDetails(new AccountHolder());
		Assert.assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@Test
	public void testReliableInvoke() {
		ResponseEntity<String> response = controller.reliable(new AccountHolder());
		Assert.assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
	}
}
