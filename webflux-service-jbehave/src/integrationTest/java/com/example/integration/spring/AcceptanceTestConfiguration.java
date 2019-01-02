package com.example.integration.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.example.ffs.WebFluxServiceApplication;

@Configuration
@Import(WebFluxServiceApplication.class)
@ComponentScan
public class AcceptanceTestConfiguration {

}
