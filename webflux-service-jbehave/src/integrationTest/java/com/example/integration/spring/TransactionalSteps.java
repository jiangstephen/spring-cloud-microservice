package com.example.integration.spring;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Steps
public class TransactionalSteps {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(TransactionalSteps.class);
	
	@Autowired
	private PlatformTransactionManager transactionManager;

	private TransactionStatus transaction;
	
	@BeforeScenario
	public void beforeScenario(){
		transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());
		transaction.setRollbackOnly();
		LOGGER.info("initialize the transaction {}", transaction);
	}
	
	@AfterScenario
	public void afterScenario(){
		if(transaction != null){
			transactionManager.rollback(transaction);
			//transactionManager.rollback(TransactionAspectSupport.currentTransactionStatus());
			LOGGER.info("Rollback the transaction {}", transaction);
		}
	}
}
