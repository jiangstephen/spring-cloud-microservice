package com.example.ffs.h2;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.ffs.client.Movie;

@Repository
public interface MovieH2Repository extends CrudRepository<Movie, String> {

}
