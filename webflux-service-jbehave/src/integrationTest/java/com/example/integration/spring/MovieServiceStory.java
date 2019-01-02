package com.example.integration.spring;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AcceptanceTest
public class MovieServiceStory extends AbstractSpringJBehaveStory {
	
	@LocalServerPort
	private int port;
	
	@Bean
	public WebClient webClient(){
		return WebClient.builder().baseUrl("http://localhost:"+port+"/movies").build();
	}
	
	@Test
	public void getPort(){
		Assert.assertThat(port, Matchers.greaterThan(0));
	}

}
