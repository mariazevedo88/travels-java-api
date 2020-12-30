package io.github.mariazevedo88.travelsjavaapi.service.user;

import java.util.Optional;

import io.github.mariazevedo88.travelsjavaapi.model.user.UserAccount;

/**
 * Interface that provides methods for manipulating User Account objects.
 * 
 * @author Mariana Azevedo
 * @since 13/10/2020
 */
public interface UserAccountService {
	
	/**
	 * Method that saves an User Account in the database.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/10/2020
	 * 
	 * @param userAccount
	 * @return UserAccount object
	 */
	UserAccount save(UserAccount userAccount);
	
	/**
	 * Method that find an UserAccount by user's id and account's id.
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
