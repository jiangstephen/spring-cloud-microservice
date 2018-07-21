package com.example.reservationclient;


import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.Data;

@EnableBinding(Source.class)
@EnableCircuitBreaker
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class ReservationClientApplication {

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ReservationClientApplication.class, args);
	}
}

@RestController
@RequestMapping("/reservations")
class ReservationApiGatewayRestController {
	
	private final Logger LOG = LoggerFactory.getLogger(ReservationApiGatewayRestController.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Source source;
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	public Collection<String> getReservationNamesFallback(){
		return new ArrayList<>();
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void writeReservation(@RequestBody Reservation reservation){
		Message<String> msg = MessageBuilder.withPayload(reservation.getReservationName())
				.setHeader(KafkaHeaders.MESSAGE_KEY, reservation.getReservationName().getBytes()).build();
		this.source.output().send(msg);
		LOG.info("send message {}", msg);
		
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, value="/new")
	public void writeReservation2(@RequestBody Reservation reservation){
		Message<String> msg = MessageBuilder.withPayload(reservation.getReservationName())
				.setHeader(KafkaHeaders.MESSAGE_KEY, reservation.getReservationName().getBytes()).build();
		this.kafkaTemplate.send("reservations", reservation.getReservationName());
	}
	
	@HystrixCommand(fallbackMethod="getReservationNamesFallback")
	@RequestMapping(method = RequestMethod.GET, value = "/names")
	public Collection<String>  getReservationNames(){
		ParameterizedTypeReference<Resources<Reservation>>  ptr = new ParameterizedTypeReference<Resources<Reservation>>() {
		};
		ResponseEntity<Resources<Reservation>> entity = 
				this.restTemplate.exchange("http://reservation-service/reservations", HttpMethod.GET, null, ptr);
		return entity.getBody().getContent().stream().map(Reservation::getReservationName).collect(Collectors.toList());
	}
}

@Data
class Reservation {
	private String reservationName;
}
