package io.github.mariazevedo88.travelsjavaapi.service.account;

import java.util.Optional;

import io.github.mariazevedo88.travelsjavaapi.model.account.Account;

/**
 * Interface that provides methods for manipulating Account objects.
 * 
 * @author Mariana Azevedo
 * @since 25/10/2020
 */
public interface AccountService {
	
	/**
	 * Method that saves an account in the database.
	 * 
	 * @author Mariana Azevedo
	 * @since 25/10/2020
	 * 
	 * @param account
	 * @return <code>Account</code> object
	 */
	Account save(Account account);
	
	/**
	 * Method that find an account by accountNumber in the database.
	 * 
	 * @author Mariana Azevedo
	 * @since 25/10/2020
	 * 
	 * @param accountNumber
	 * @return Optional<Account> object
	 */
	Optional<Account> findByAccountNumber(String accountNumber);
	
	/**
	 * Method that find a account by an id.
	 * 
	 * @author Mariana Azevedo
	 * @since 30/10/2020
	 * 
	 * @param id
	 * @return <code>Optional<Account></code> object
	 */
	Optional<Account> findById(Long accountId);

}
