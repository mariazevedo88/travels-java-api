package io.github.mariazevedo88.financialjavaapi.test.repository.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

import io.github.mariazevedo88.financialjavaapi.model.enumeration.TransactionTypeEnum;
import io.github.mariazevedo88.financialjavaapi.model.v1.transaction.Transaction;
import io.github.mariazevedo88.financialjavaapi.repository.transaction.TransactionRepository;

/**
 * Class that implements tests of the TransactionRepository funcionalities
 * 
 * @author Mariana Azevedo
 * @since 04/04/2020
 */
@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class TransactionRepositoryTest {
	
	@Autowired
	private TransactionRepository repository;
	
	/**
	 * Method to setup an User to use in the tests.
	 * 
	 * @author Mariana Azevedo
	 * @since 24/03/2020
	 */
	@BeforeAll
	private void setUp() {
		
		Transaction transaction = new Transaction();
		transaction.setNsu("220788");
		transaction.setAuthorizationNumber("000123");
		transaction.setTransactionDate(new Date());
		transaction.setAmount(new BigDecimal(100d));
		transaction.setType(TransactionTypeEnum.CARD);
		
		repository.save(transaction);
	}
	
	/**
	 * Method that test the repository that save an User in the API.
	 * 
	 * @author Mariana Azevedo
	 * @since 24/03/2020
	 */
	@Test
	public void testSave() {
		
		Transaction transaction = new Transaction();
		transaction.setNsu("270257");
		transaction.setAuthorizationNumber("000123");
		transaction.setTransactionDate(new Date());
		transaction.setAmount(new BigDecimal(100d));
		transaction.setType(TransactionTypeEnum.CARD);
		
		Transaction response = repository.save(transaction);
		
		assertNotNull(response);
	}
	
	/**
	 * Method that test the repository that search for an User by the email.
	 * 
	 * @author Mariana Azevedo
	 * @since 24/03/2020
	 */
	@Test
	public void testFindByNsu(){
		
		List<Transaction> response = repository.findByNsu("220788");
		
		assertFalse(response.isEmpty());
		assertEquals("220788", response.get(0).getNsu());
	}
	
	/**
	 * Method to remove all User test data.
	 * 
	 * @author Mariana Azevedo
	 * @since 24/03/2020
	 */
	@AfterAll
	private void tearDown() {
		repository.deleteAll();
	}

}
