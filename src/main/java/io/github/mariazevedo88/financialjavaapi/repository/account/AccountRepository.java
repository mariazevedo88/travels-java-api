package io.github.mariazevedo88.financialjavaapi.repository.account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.mariazevedo88.financialjavaapi.model.account.Account;

/**
 * Interface that implements the Account Repository, with JPA CRUD methods.
 *  
 * @author Mariana Azevedo
 * @since 25/10/2020
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	/**
	 * Method to search an Account by the account number.
	 * 
	 * @author Mariana Azevedo
	 * @since 25/10/2020
	 * 
	 * @param email
	 * @return Optional<Account>
	 */
	Optional<Account> findByAccountNumber(String accountNumber);
}
