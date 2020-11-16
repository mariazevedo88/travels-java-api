package io.github.mariazevedo88.financialjavaapi.test.repository.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
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
import io.github.mariazevedo88.financialjavaapi.model.account.Account;
import io.github.mariazevedo88.financialjavaapi.repository.account.AccountRepository;

/**
 * Class that implements tests of the AccountRepositoryTest funcionalities
 * 
 * @author Mariana Azevedo
 * @since 25/10/2020
 */
@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class AccountRepositoryTest {
	
	@Autowired
	private AccountRepository repository;
	
	static final String ACCOUNT_NUMBER = "12345-60";
	
	/**
	 * Method to setup an Account to use in the tests.
	 * 
	 * @author Mariana Azevedo
	 * @since 25/10/2020
	 */
	@BeforeAll
	private void setUp() {
		
		Account account = new Account();
		account.setAccountNumber(ACCOUNT_NUMBER);
		account.setAccountType(AccountTypeEnum.CHECKING_ACCOUNT);
		
		repository.save(account);
	}
	
	/**
	 * Method that test the repository that save an User in the API.
	 * 
	 * @author Mariana Azevedo
	 * @since 24/03/2020
	 */
	@Test
	public void testSave() {
		
		Account account = new Account();
		account.setAccountNumber("98756-401");
		account.setAccountType(AccountTypeEnum.SAVINGS_ACCOUNT);
		
		Account response = repository.save(account);
		
		assertNotNull(response);
	}
	
	/**
	 * Method that test the repository that search for an Account by the account number.
	 * 
	 * @author Mariana Azevedo
	 * @since 24/03/2020
	 */
	@Test
	public void testFindByAccountNumber(){
		Optional<Account> response = repository.findByAccountNumber(ACCOUNT_NUMBER);
		
		assertTrue(response.isPresent());
		assertEquals(ACCOUNT_NUMBER, response.get().getAccountNumber());
	}
	
	/**
	 * Method to remove all Account test data.
	 * 
	 * @author Mariana Azevedo
	 * @since 25/10/2020
	 */
	@AfterAll
	private void tearDown() {
		repository.deleteAll();
	}

}
