package com.devsuperior.movieflix.services;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.GenreDTO;
import com.devsuperior.movieflix.dto.MovieDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.exceptions.DatabaseException;
import com.devsuperior.movieflix.exceptions.ResourceNotFoundException;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;

@Service
public class GenreService implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private GenreRepository repository;
	
	@Autowired
	private MovieRepository movieRepository;

	@Transactional(readOnly = true)
	public List<GenreDTO> findAll() {
		List<Genre> list = repository.findAll();
	
		return list.stream().map(x -> new GenreDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public Page<GenreDTO> findAllPaged(PageRequest pageRequest){
		Page<Genre> page = repository.findAll(pageRequest);
		
		return page.map(x -> new GenreDTO(x));
	}
	
	@Transactional(readOnly = true)
	public GenreDTO findById(Long id) {
		Optional<Genre> obj = repository.findById(id);
		Genre entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		
		return new GenreDTO(entity);
	}
	
	@Transactional
	public GenreDTO insert(GenreDTO dto) {
		Genre entity = new Genre();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		
		return new GenreDTO(entity);
	}

	@Transactional
	public GenreDTO update(Long id, GenreDTO dto) {
		try {
			Genre entity = repository.getOne(id);
			copyDtoToEntity(dto, entity);
			
			return new GenreDTO(entity);
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
	
	private void copyDtoToEntity(GenreDTO dto, Genre entity) {
		entity.setName(dto.getName());
		entity.getMovies().clear();
		
		for(MovieDTO movieDTO: dto.getMovies()) {
			Movie movie = movieRepository.getOne(movieDTO.getId());
			entity.getMovies().add(movie);
		}
	}
}
