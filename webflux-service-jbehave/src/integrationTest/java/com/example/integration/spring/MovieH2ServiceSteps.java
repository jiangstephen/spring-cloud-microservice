package com.example.integration.spring;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.TransactionStatus;

import com.example.ffs.client.Movie;
import com.example.ffs.h2.MovieH2Repository;

@Steps
@Scope("scenario")
public class MovieH2ServiceSteps {

	private final static Logger LOGGER = LoggerFactory.getLogger(MovieH2ServiceSteps.class);
	
	@Autowired
	private MovieH2Repository movieRepository;
	
	public MovieH2ServiceSteps() {
		LOGGER.info("initializing the bean for the scenario");
	}
	
	@Given("in h2, the movie $movieId with name $movieName is stored")
	public void storeMovieWithIdAndName(String movieId, String movieName) {
		LOGGER.info("invoke storeMovieWithIdAndName with id={} and name={}", movieId, movieName);
		String title = movieRepository.save(new Movie(movieId, movieName)).getTitle();
		Assert.assertEquals(movieName, title);
		printCurrentTransactionStatus();
	}

	@Given("in h2, doing nothing")
	public void doingNothing() {
		printCurrentTransactionStatus();
	}

	@Then("in h2, the movie with id $movieId should have name $movieName")
	public void retrieveMovieWithIdAndVerifyTheName(String movieId, String movieName) {
		LOGGER.info("invoke retrieveMovieWithIdAndVerifyTheName with id={} and name={}", movieId, movieName);
		String title = movieRepository.findById(movieId).get().getTitle();
		Assert.assertEquals(movieName, title);
		printCurrentTransactionStatus();
	}
	

	@Then("in h2, the movie with id $movieId should not exist")
	public void retrieveMovieWithIdAndVerifyNotFound(String movieId) {
		LOGGER.info("invoke retrieveMovieWithIdAndVerifyNotFound with id={}", movieId);
		Assert.assertFalse(movieRepository.findById(movieId).isPresent());
		printCurrentTransactionStatus();
	}
	
	private void printCurrentTransactionStatus(){
		TransactionStatus txStatus = null; //TransactionAspectSupport.currentTransactionStatus();
		LOGGER.info("The current transaction is {}", txStatus);
	}
}
