package com.example.reservationservice;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ReservationServiceApplication {

	@Bean
	CommandLineRunner commandLineRunner(ReservationRepository reservationRepository){
		return strings -> Stream.of("Stephen", "Josh", "Peter", "Susan", "Kevin").forEach(
				n -> reservationRepository.save(new Reservation(n)));
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ReservationServiceApplication.class, args);
	}
}

@RefreshScope
@RestController
class MessageRestController{
	@Value("${message}")
	private String message;
	
	@RequestMapping("/message")
	public String message(){
		return this.message;
	}
}
