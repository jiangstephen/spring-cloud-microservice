package com.example.integration.spring;

import javax.annotation.PostConstruct;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.ffs.client.Movie;

@Steps
@Order(Ordered.LOWEST_PRECEDENCE)
public class MovieServiceSteps {

	private final static Logger LOGGER = LoggerFactory.getLogger(MovieServiceSteps.class);

	@Autowired
	private Environment env;

	private WebClient webClient;

	private synchronized WebClient getWebClient() {
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

	@Given("the movie $movieId with name $movieName is stored")
	public void storeMovieWithIdAndName(int movieId, String movieName) {
		LOGGER.info("invoke storeMovieWithIdAndName with id={} and name={}", movieId, movieName);
		String title = getWebClient().post().uri("/save/" + movieId + "/" + movieName).retrieve().bodyToMono(Movie.class)
				.block().getTitle();
		Assert.assertEquals(movieName, title);
	}

	@Given("doing nothing")
	public void doingNothing() {
	}

	@Then("the movie with id $movieId should have name $movieName")
	public void retrieveMovieWithIdAndVerifyTheName(int movieId, String movieName) {
		LOGGER.info("invoke retrieveMovieWithIdAndVerifyTheName with id={} and name={}", movieId, movieName);
		String title = getWebClient().get().uri("/" + movieId).retrieve().bodyToMono(Movie.class).block().getTitle();
		Assert.assertEquals(movieName, title);
	}

	private void sleep(int milliSecs) {
		try {
			Thread.sleep(milliSecs);
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}

}
