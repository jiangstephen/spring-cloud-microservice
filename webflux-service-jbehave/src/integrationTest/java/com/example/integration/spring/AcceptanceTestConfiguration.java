package com.example.integration.spring;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.example.ffs.WebFluxServiceApplication;

@Configuration
@Import(WebFluxServiceApplication.class)
@ComponentScan
public class AcceptanceTestConfiguration {
	
	
	@Bean
	public CustomScopeConfigurer customScopeConfigurer(ScenarioScope scenarioScope){
		CustomScopeConfigurer customScopeConfigurer = new CustomScopeConfigurer();
		customScopeConfigurer.addScope("scenario", scenarioScope);
		return customScopeConfigurer;
	}

}
