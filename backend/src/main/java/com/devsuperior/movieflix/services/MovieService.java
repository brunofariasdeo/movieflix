package com.devsuperior.movieflix.services;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.exceptions.DatabaseException;
import com.devsuperior.movieflix.exceptions.ResourceNotFoundException;
import com.devsuperior.movieflix.repositories.GenreRepository;
//import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;

@Service
public class MovieService implements Serializable{
	private static final long serialVersionUID = 1L;

	@Autowired
	private MovieRepository repository;
	
	@Autowired
	private GenreRepository genreRepository;
	
	@Transactional(readOnly = true)
	public Page<MovieDTO> findAllPaged(Long genreId, String title, PageRequest pageRequest){
		Genre genre = (genreId == 0) ? null : genreRepository.getOne(genreId);
		Page<Movie> list = repository.find(genre, pageRequest);

		return list.map(x -> new MovieDTO(x));
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
