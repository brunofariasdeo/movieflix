package com.devsuperior.movieflix.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.exceptions.DatabaseException;
import com.devsuperior.movieflix.exceptions.ResourceNotFoundException;
import com.devsuperior.movieflix.repositories.MovieRepository;

@Service
public class MovieService {
	
	@Autowired
	private MovieRepository repository;
	
	@Transactional(readOnly = true)
	public List<MovieDTO> findAll(){
		List<Movie> list = repository.findAll();
		
		List<MovieDTO> listDTO = new ArrayList<>();
		
		for(Movie movie : list) {
			listDTO.add(new MovieDTO(movie));
		}
		
		return listDTO;
	}

	@Transactional(readOnly = true)
	public MovieDTO findById(Long id) {
		Optional<Movie> obj = repository.findById(id);
		Movie entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		
		return new MovieDTO(entity);
	}
	
	@Transactional
	public MovieDTO insert(MovieDTO dto) {
		Movie entity = new Movie();
		entity.setTitle(dto.getTitle());
		entity.setSubtitle(dto.getSubtitle());
		entity.setYear(dto.getYear());
		entity.setImgUrl(dto.getImgUrl());
		entity.setSynopsis(dto.getSynopsis());
		entity = repository.save(entity);
		
		return new MovieDTO(entity);
	}

	@Transactional
	public MovieDTO update(Long id, MovieDTO dto) {
		try {
			Movie entity = repository.getOne(id);
			entity.setTitle(dto.getTitle());
			entity.setSubtitle(dto.getSubtitle());
			entity.setYear(dto.getYear());
			entity.setImgUrl(dto.getImgUrl());
			entity.setSynopsis(dto.getSynopsis());
			entity = repository.save(entity);
			
			return new MovieDTO(entity);
		}
		catch(EntityNotFoundException e){
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	public void delete(Long id) {
		try { 
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);

		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");

		}
	}
}
