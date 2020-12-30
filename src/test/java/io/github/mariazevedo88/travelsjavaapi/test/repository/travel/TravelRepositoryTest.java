package io.github.mariazevedo88.travelsjavaapi.test.repository.travel;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import io.github.mariazevedo88.travelsjavaapi.enumeration.AccountTypeEnum;
import io.github.mariazevedo88.travelsjavaapi.enumeration.TravelTypeEnum;
import io.github.mariazevedo88.travelsjavaapi.model.account.Account;
import io.github.mariazevedo88.travelsjavaapi.model.travel.Travel;
import io.github.mariazevedo88.travelsjavaapi.repository.account.AccountRepository;
import io.github.mariazevedo88.travelsjavaapi.repository.travel.TravelRepository;

/**
 * Class that implements tests of the TravelRepository features
 * 
 * @author Mariana Azevedo
 * @since 24/03/2020
 */
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
public class TravelRepositoryTest {
	
	@Autowired
	private TravelRepository repository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	static final String ACCOUNT_NUMBER = "5698745";
	
	Account account;
	
	/**
	 * Method to setup an object Travel to use in the tests.
	 * 
	 * @author Mariana Azevedo
	 * @since 24/03/2020
	 */
	@BeforeAll
	private void setUp() {
		
		account = new Account(null, ACCOUNT_NUMBER, AccountTypeEnum.BASIC);
		accountRepository.save(account);
		
		Travel travel = new Travel(null, "220788", LocalDateTime.now(), 
			null, new BigDecimal(100d), TravelTypeEnum.ONE_WAY,
			account);
		
		repository.save(travel);
	}
	
	/**
	 * Method that test the repository that save an object Travel in the API.
	 * 
	 * @author Mariana Azevedo
	 * @since 24/03/2020
	 */
	@Test
	@Order(1)
	public void testSave() {
		
		Travel travel = new Travel(null, "270257", LocalDateTime.now(), 
				null, new BigDecimal(100d), TravelTypeEnum.ONE_WAY,
				account);
		
		Travel response = repository.save(travel);
		assertNotNull(response);
	}
	
	/**
	 * Method that test the repository that search for an object Travel by the orderNumber.
	 * 
	 * @author Mariana Azevedo
	 * @since 24/03/2020
	 */
	@Test
	@Order(2)
	public void testFindByOrderNumber(){
		
		Optional<Travel> response = repository.findByOrderNumber("220788");
		assertFalse(response.isEmpty());
	}
	
	/**
	 * Method to remove all travels test data.
	 * 
	 * @author Mariana Azevedo
	 * @since 24/03/2020
	 */
	@AfterAll
	private void tearDown() {
		repository.deleteAll();
		accountRepository.delete(account);
	}

}
