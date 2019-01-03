package com.example.ffs;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.time.Duration;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

import com.example.ffs.client.Movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class WebFluxServiceConfiguration {
	
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
				.andRoute(POST("/movie/save/{id}/{name}"), 
						req -> ok().body(ms.saveMovie(new Movie(req.pathVariable("id"), req.pathVariable("name"))), Movie.class))
				.andRoute(GET("/movie/{id}/events"), 
						req -> ok().contentType(MediaType.TEXT_EVENT_STREAM).body(ms.getEvents(req.pathVariable("id")), MovieEvent.class));
	}
}

@Repository
interface MovieRepository extends ReactiveCrudRepository<Movie, String> {
	Flux<Movie> findByTitle(String title);
}

@Service
class MovieService {
	
	@Autowired
	private MovieRepository movieRepository;
	
	public Flux<Movie> getAllMovies(){
		return this.movieRepository.findAll();
	}
	
	public Mono<Movie> getMovieById(String id){
		return this.movieRepository.findById(id);
	}
	
	public Mono<Movie> saveMovie(Movie movie){
		return this.movieRepository.save(movie);
	}
	
	public Flux<MovieEvent> getEvents(String movieId){
		return Flux.<MovieEvent>generate(sink -> sink.next(new MovieEvent(movieId, new Date()))).delayElements(Duration.ofSeconds(1));
	}
}


@Document
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
class MovieEvent {
	@Id
	private String movieId;
	private Date dateViewed;
}

