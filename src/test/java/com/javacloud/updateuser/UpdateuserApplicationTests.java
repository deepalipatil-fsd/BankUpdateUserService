package com.javacloud.updateuser;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.when;

@SpringBootTest
class UpdateuserApplicationTests {
	@InjectMocks
	UpdateuserApplication updateuserApplication;
	@Mock
	RestTemplateBuilder builder;
	@Mock
	RestTemplate restTemplate;
	@Test
	void testUpdateUserApplication() {
		String[] args = {};
		UpdateuserApplication.main(args);
		when(builder.build()).thenReturn(restTemplate);
		Assert.assertEquals(restTemplate, updateuserApplication.rest(builder));
	}

}
