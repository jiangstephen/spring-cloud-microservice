package com.example.ffs;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.time.Duration;
import java.util.Date;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.ffs.client.Movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class WebFluxServiceApplication {

	@Bean
	ApplicationRunner demoData(MovieRepository movieRepository) {
		return args -> {
			movieRepository.deleteAll()
					.thenMany(Flux
							.just("The Silence of th Lambdas", "Back to the future", "Aeon Flux", "Meet the Fluxers",
									"The Fluxxinator", "Flux Gordon", "Y Tu Mono Tambien")
							.map(Movie::new).flatMap(movieRepository::save))
					.thenMany(movieRepository.findAll()).subscribe(System.out::println);
		};
	}
	
	@Bean
	RouterFunction<?> routerFunction(MovieService ms){
		return RouterFunctions.route(GET("/movies"), 
						req -> ok().body(ms.getAllMovies(), Movie.class))
				.andRoute(GET("/movie/{id}"), 
						req -> ok().body(ms.getMovieById(req.pathVariable("id")), Movie.class))
				.andRoute(GET("/movie/{id}/events"), 
						req -> ok().contentType(MediaType.TEXT_EVENT_STREAM).body(ms.getEvents(req.pathVariable("id")), MovieEvent.class));
	}

	public static void main(String args[]) {
		SpringApplication.run(WebFluxServiceApplication.class, args);
	}
}

@Repository
interface MovieRepository extends ReactiveCrudRepository<Movie, String> {
	Flux<Movie> findByTitle(String title);
}

@Service
class MovieService {
	private final MovieRepository movieRepository;
	
	MovieService(MovieRepository movieRepository){
		this.movieRepository = movieRepository;
	}
	
	public Flux<Movie> getAllMovies(){
		return this.movieRepository.findAll();
	}
	
	public Mono<Movie> getMovieById(String id){
		return this.movieRepository.findById(id);
	}
	
	public Flux<MovieEvent> getEvents(String movieId){
		return Flux.<MovieEvent>generate(sink -> sink.next(new MovieEvent(movieId, new Date()))).delayElements(Duration.ofSeconds(1));
	}
}

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
class MovieEvent {
	private String movieId;
	private Date dateViewed;
}

