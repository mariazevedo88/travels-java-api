package io.github.mariazevedo88.travelsjavaapi.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.mariazevedo88.travelsjavaapi.model.user.UserAccount;

/**
 * Interface that implements the User Account Repository, with JPA CRUD methods
 * and other customized searches.
 *  
 * @author Mariana Azevedo
 * @since 13/10/2020
 */
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

	/**
	 * Method to search an UserAccount by user's id and account id.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/10/2020
	 * 
	 * @param user
	 * @param account
	 * @return Optional<UserAccount>
	 */
	Optional<UserAccount> findByUserIdAndAccountId(Long user, Long account);
}