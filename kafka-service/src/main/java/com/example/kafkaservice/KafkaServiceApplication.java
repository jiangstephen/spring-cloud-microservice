package com.example.kafkaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.test.rule.KafkaEmbedded;

@SpringBootApplication
public class KafkaServiceApplication {
	
	public static void main(String[] args) {
		KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, "topic");
		System.setProperty("spring.kafka.bootstrap-servers", embeddedKafka.getBrokersAsString());
		SpringApplication.run(KafkaServiceApplication.class, args);
	}
}
