package com.example.ffc;

import org.reactivestreams.Publisher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@SpringBootApplication
public class WebFluxClientApplication {

	
	@Bean
	public WebClient webClient(){
		return WebClient.builder().baseUrl("http://localhost:8090/movies").build();
	}
	
	@Bean
	public RouterFunction<ServerResponse> router(WebClient webClient){
		Publisher<String> names = webClient.get().retrieve().bodyToFlux(Movie.class).map(movie -> movie.getTitle());
		return RouterFunctions.route(RequestPredicates.GET("/titles"), req -> ServerResponse.ok().body(names, String.class));
	}
	
	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder){
		 return routeLocatorBuilder.routes().route(r -> r.path("/proxy").uri("http://localhost:8090/movie")).build();
		
	}
	public static void main(String[] args) {
		SpringApplication.run(WebFluxClientApplication.class, args);
	}
}
