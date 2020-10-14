package io.github.mariazevedo88.financialjavaapi.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.mariazevedo88.financialjavaapi.model.user.UserTransaction;

/**
 * Interface that implements the User Transaction Repository, with JPA CRUD methods
 * and other customized searches.
 *  
 * @author Mariana Azevedo
 * @since 13/10/2020
 */
public interface UserTransactionRepository extends JpaRepository<UserTransaction, Long> {

	/**
	 * Method to search an UserTransaction by user's id and transaction id.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/10/2020
	 * 
	 * @param user
	 * @param transaction
	 * @return Optional<UserTransaction>
	 */
	Optional<UserTransaction> findByUserIdAndTransactionId(Long user, Long transaction);
}