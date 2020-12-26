package io.github.mariazevedo88.financialjavaapi.test.repository.user;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import io.github.mariazevedo88.financialjavaapi.enumeration.AccountTypeEnum;
import io.github.mariazevedo88.financialjavaapi.enumeration.RoleEnum;
import io.github.mariazevedo88.financialjavaapi.model.account.Account;
import io.github.mariazevedo88.financialjavaapi.model.user.User;
import io.github.mariazevedo88.financialjavaapi.model.user.UserAccount;
import io.github.mariazevedo88.financialjavaapi.repository.account.AccountRepository;
import io.github.mariazevedo88.financialjavaapi.repository.user.UserAccountRepository;
import io.github.mariazevedo88.financialjavaapi.repository.user.UserRepository;

/**
 * Class that implements tests of the UserAccountRepository functionalities
 * 
 * @author Mariana Azevedo
 * @since 06/12/2020
 */
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
public class UserAccountRepositoryTest {
	
	static final String EMAIL = "main@test.com";
	static final String ACCOUNT_NUMBER = "99988-7";
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private UserAccountRepository userAccRepository;
	
	Account account;
	
	User user;
	
	UserAccount userAccount;
	
	/**
	 * Method to setup an User and Account to use in the tests.
	 * 
	 * @author Mariana Azevedo
	 * @since 24/03/2020
	 */
	@BeforeAll
	private void setUp() {
		
		user = new User(null, "Main User", "9999", EMAIL, 
				RoleEnum.ROLE_ADMIN);
		userRepository.save(user);
		
		account = new Account(null, ACCOUNT_NUMBER, 
				AccountTypeEnum.SAVINGS_ACCOUNT);
		accountRepository.save(account);
	}
	
	/**
	 * Method that test the repository that save an UserAccount in the API.
	 * 
	 * @author Mariana Azevedo
	 * @since 24/03/2020
	 */
	@Test
	public void testSave() {
		
		userAccount = new UserAccount(null, user, account);
		userAccRepository.save(userAccount);
	}
	
	/**
	 * Method to remove all UserAccount test data.
	 * 
	 * @author Mariana Azevedo
	 * @since 08/12/2020
	 */
//	@AfterAll
//	private void tearDown() {
//		userAccRepository.delete(userAccount);
//		userRepository.delete(user);
//		accountRepository.delete(account);
//	}

}
