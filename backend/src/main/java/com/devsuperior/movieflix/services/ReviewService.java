package com.devsuperior.movieflix.services;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.exceptions.DatabaseException;
import com.devsuperior.movieflix.exceptions.ResourceNotFoundException;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.repositories.UserRepository;

@Service
public class ReviewService implements Serializable{
	private static final long serialVersionUID = 1L;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private ReviewRepository repository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthService authService;
	
	@Transactional(readOnly = true)
	public List<ReviewDTO> findAll() {
		List<Review> reviews = repository.findAll();
		
		return reviews.stream().map(review -> new ReviewDTO(review)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public ReviewDTO findById(Long id) {
		Optional<Review> obj = repository.findById(id);
		Review entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		
		return new ReviewDTO(entity);
	}
	
	@Transactional
	public ReviewDTO insert(ReviewDTO dto) {
		User user = authService.authenticated();
		
		authService.validateSelfOrMember(user.getId());
		
		Review review = new Review();
		
		Movie movie = movieRepository.getOne(dto.getMovieId());
		
		review.setUser(user);
		review.setMovie(movie);
		review.setText(dto.getText());
		
		review = repository.save(review);
		
		return new ReviewDTO(review);
	}

	@Transactional
	public ReviewDTO update(Long id, ReviewDTO dto) {
		try {
			Review review = repository.getOne(id);
			
			Movie movie = movieRepository.getOne(dto.getMovieId());
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User userAuthenticated = userRepository.findByEmail(authentication.getName());
			
			review.setUser(userAuthenticated);
			review.setMovie(movie);
			review.setText(dto.getText());
			
			review = repository.save(review);
			
			return new ReviewDTO(review);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id " + id + " not found");
		}
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id " + id + " not found");
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
}
