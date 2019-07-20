package com.example.integration.spring;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Steps
@Scope("scenario")
public class TransactionalSteps {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(TransactionalSteps.class);
	
	@Autowired
	private PlatformTransactionManager transactionManager;

	private TransactionStatus transaction;
	
	public TransactionalSteps() {
		LOGGER.info("Initializing the transactional steps");
	}
	
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
			LOGGER.info("Rollback the transaction {}", transaction);
		}
	}
}
