package com.devsuperior.movieflix.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.devsuperior.movieflix.entities.Genre;

public class GenreDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	
	private Set<MovieDTO> movies = new HashSet<>();
	
	public GenreDTO() {
		
	}

	public GenreDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public GenreDTO(Genre entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		entity.getMovies().forEach(
			movie -> this.movies.add(new MovieDTO(movie))
		);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<MovieDTO> getMovies() {
		return movies;
	}
}
