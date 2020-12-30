package io.github.mariazevedo88.travelsjavaapi.service.user;

import java.util.Optional;

import io.github.mariazevedo88.travelsjavaapi.model.user.User;

/**
 * Interface that provides methods for manipulating User objects.
 * 
 * @author Mariana Azevedo
 * @since 11/10/2020
 */
public interface UserService {

	/**
	 * Method that saves an user in the database.
	 * 
	 * @author Mariana Azevedo
	 * @since 11/10/2020
	 * 
	 * @param user
	 * @return User object
	 */
	User save(User user);
	
	/**
	 * Method that find an user by email in the database.
	 * 
	 * @author Mariana Azevedo
	 * @since 11/10/2020
	 * 
	 * @param email
	 * @return Optional<User> object
	 */
	Optional<User> findByEmail(String email);
}
