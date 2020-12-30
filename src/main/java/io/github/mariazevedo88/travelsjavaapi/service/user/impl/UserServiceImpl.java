package io.github.mariazevedo88.travelsjavaapi.service.user.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.mariazevedo88.travelsjavaapi.model.user.User;
import io.github.mariazevedo88.travelsjavaapi.repository.user.UserRepository;
import io.github.mariazevedo88.travelsjavaapi.service.user.UserService;

/**
 * Class that implements the user's service methods
 * 
 * @author Mariana Azevedo
 * @since 11/10/2020
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository repository;

	/**
	 * @see UserService#save(User)
	 */
	@Override
	public User save(User user) {
		return repository.save(user);
	}

	/**
	 * @see UserService#findByEmail(String)
	 */
	@Override
	public Optional<User> findByEmail(String email) {
		return repository.findByEmail(email);
	}

}
