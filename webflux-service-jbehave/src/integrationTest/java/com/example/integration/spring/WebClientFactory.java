package com.example.integration.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientFactory {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WebClientFactory.class);
	
	private WebClient webClient;
	
	@Autowired
	private Environment env;
	
	public synchronized WebClient getWebClient() {
		if(webClient == null){
			String serverPort = null;
			while (serverPort == null) {
				serverPort = env.getProperty("local.server.port");
				sleep(100);
			}
			webClient = WebClient.builder().baseUrl("http://localhost:" + serverPort + "/movie").build();
			LOGGER.info("initialized the web client {}", webClient);
		}
		return webClient;
	}
	
	private void sleep(int milliSecs) {
		try {
			Thread.sleep(milliSecs);
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}

}
