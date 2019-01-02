package com.example.integration;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IncreaseSteps {
	
	private static Logger LOGGER = LoggerFactory.getLogger(IncreaseSteps.class);

	private int counter;
	
	public IncreaseSteps(){
		LOGGER.info("Invoked the constructor");
	}

	@Given("a counter")
	public void aCounter() {
	}

	@Given("the counter has the initial value $value")
	public void counterHasAnyIntegralValue(int initialValue) {
		counter = initialValue;
	}

	@When("the user increases the counter")
	public void increasesTheCounter() {
		counter++;
	}

	@Then("the value of the counter must be $value")
	public void theValueOfTheCounterMustBe1Greater(int finalValue) {
		Assert.assertEquals(finalValue ,counter);
	}
}