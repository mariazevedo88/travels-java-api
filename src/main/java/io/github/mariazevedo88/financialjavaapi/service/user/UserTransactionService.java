package io.github.mariazevedo88.financialjavaapi.service.user;

import java.util.Optional;

import io.github.mariazevedo88.financialjavaapi.model.user.UserTransaction;

/**
 * Interface that provides methods for manipulating User Transaction objects.
 * 
 * @author Mariana Azevedo
 * @since 13/10/2020
 */
public interface UserTransactionService {
	
	/**
	 * Method that saves an User Transaction in the database.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/10/2020
	 * 
	 * @param userTransaction
	 * @return UserTransaction object
	 */
	UserTransaction save(UserTransaction userTransaction);
	
	/**
	 * Method that find an UserTransaction by user's id and transaction's id.
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
