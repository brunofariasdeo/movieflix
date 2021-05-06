package com.devsuperior.movieflix.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.UserDTO;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	@Transactional(readOnly=true)
	public List<UserDTO> findAll(){
		List<User> list = repository.findAll();
		
		List<UserDTO> listDTO = new ArrayList<>();
		
		for(User user : list) {
			listDTO.add(new UserDTO(user));
		}
		
		return listDTO;
	}
}
