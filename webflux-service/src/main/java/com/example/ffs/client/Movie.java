package com.example.ffs.client;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Movie {
	@Id
	private String id;
	@NonNull
	private String title;
}