package com.example.ffs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(WebFluxServiceConfiguration.class)
public class WebFluxServiceApplication {

	public static void main(String args[]) {
		SpringApplication.run(WebFluxServiceApplication.class, args);
	}
}

