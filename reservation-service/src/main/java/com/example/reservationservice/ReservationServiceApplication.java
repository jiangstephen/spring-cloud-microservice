package com.example.reservationservice;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.sleuth.sampler.ProbabilityBasedSampler;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import brave.sampler.Sampler;

@EnableDiscoveryClient
@SpringBootApplication
@EnableBinding(Sink.class)
public class ReservationServiceApplication {

	
//	@Bean
//	ProbabilityBasedSampler sampler(){
//		return info -> true;
//	}
	

	@Bean
	CommandLineRunner commandLineRunner(ReservationRepository reservationRepository) {
		return strings -> Stream.of("Stephen", "Josh", "Peter", "Susan", "Kevin")
				.forEach(n -> reservationRepository.save(new Reservation(n)));
	}

	public static void main(String[] args) {
		SpringApplication.run(ReservationServiceApplication.class, args);
	}
}

interface ReservationBinding {
	String RESERVATION_IN = "input";
	//@Input(RESERVATION_IN)
	//KStream<String, Reservation> reservationIn();
}

@MessageEndpoint
class ReservationProcessor {

	private final static Logger LOG = LoggerFactory.getLogger(ReservationProcessor.class);
	
	@Autowired
	private ReservationRepository reservationRepository;

	@ServiceActivator(inputChannel = Sink.INPUT)
	public void acceptNewReservation(Message<String> rn) {
		LOG.info("received reservation from the messageEndpoint {}" + rn);
		this.reservationRepository.save(new Reservation(rn.getPayload()));
	}
}

@Component
class ReservationProcessor2 {

	private final static Logger LOG = LoggerFactory.getLogger(ReservationProcessor.class);
	@Autowired
	private ReservationRepository reservationRepository;

	@StreamListener(Sink.INPUT)
	public void acceptNewReservation( Message<String> rn) {
		LOG.info("received reservation from StreamListener {}" + rn);
		this.reservationRepository.save(new Reservation(rn.getPayload()));
	}
}

@RefreshScope
@RestController
class MessageRestController {
	@Value("${message:Hello default}")
	private String message;

	@RequestMapping("/message")
	public String message() {
		return this.message;
	}
}
