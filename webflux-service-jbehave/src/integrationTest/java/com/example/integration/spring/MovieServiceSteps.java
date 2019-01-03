package com.example.integration.spring;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.example.ffs.client.Movie;

@Steps
@Order(Ordered.LOWEST_PRECEDENCE)
public class MovieServiceSteps {

	private final static Logger LOGGER = LoggerFactory.getLogger(MovieServiceSteps.class);
	
	@Autowired
	private WebClientFactory webClientFactory;

	@Given("the movie $movieId with name $movieName is stored")
	public void storeMovieWithIdAndName(int movieId, String movieName) {
		LOGGER.info("invoke storeMovieWithIdAndName with id={} and name={}", movieId, movieName);
		String title = webClientFactory.getWebClient().post().uri("/save/" + movieId + "/" + movieName).retrieve().bodyToMono(Movie.class)
				.block().getTitle();
		Assert.assertEquals(movieName, title);
	}

	@Given("doing nothing")
	public void doingNothing() {
	}

	@Then("the movie with id $movieId should have name $movieName")
	public void retrieveMovieWithIdAndVerifyTheName(int movieId, String movieName) {
		LOGGER.info("invoke retrieveMovieWithIdAndVerifyTheName with id={} and name={}", movieId, movieName);
		String title = webClientFactory.getWebClient().get().uri("/" + movieId).retrieve().bodyToMono(Movie.class).block().getTitle();
		Assert.assertEquals(movieName, title);
	}

}
