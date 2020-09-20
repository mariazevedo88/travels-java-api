package io.github.mariazevedo88.financialjavaapi.test.repository.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

import io.github.mariazevedo88.financialjavaapi.model.enumeration.TransactionTypeEnum;
import io.github.mariazevedo88.financialjavaapi.model.transaction.Transaction;
import io.github.mariazevedo88.financialjavaapi.repository.transaction.TransactionRepository;

/**
 * Class that implements tests of the TransactionRepository features
 * 
 * @author Mariana Azevedo
 * @since 24/03/2020
 */
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
public class TransactionRepositoryTest {
	
	@Autowired
	private TransactionRepository repository;
	
	/**
	 * Method to setup a Transaction to use in the tests.
	 * 
	 * @author Mariana Azevedo
	 * @since 24/03/2020
	 */
	@BeforeAll
	private void setUp() {
		
		Transaction transaction = new Transaction(null, "220788", "000123", 
			LocalDateTime.now(), new BigDecimal(100d), TransactionTypeEnum.CARD);
		
		repository.save(transaction);
	}
	
	/**
	 * Method that test the repository that save a Transaction in the API.
	 * 
	 * @author Mariana Azevedo
	 * @since 24/03/2020
	 */
	@Test
	@Order(1)
	public void testSave() {
		
		Transaction transaction = new Transaction(null, "270257", "000123", LocalDateTime.now(),
				new BigDecimal(100d), TransactionTypeEnum.CARD);
		
		Transaction response = repository.save(transaction);
		
		assertNotNull(response);
	}
	
	/**
	 * Method that test the repository that search for a Transaction by the NSU.
	 * 
	 * @author Mariana Azevedo
	 * @since 24/03/2020
	 */
	@Test
	@Order(2)
	public void testFindByNsu(){
		
		List<Transaction> response = repository.findByNsu("220788");
		assertFalse(response.isEmpty());
	}
	
	/**
	 * Method to remove all transactions test data.
	 * 
	 * @author Mariana Azevedo
	 * @since 24/03/2020
	 */
	@AfterAll
	private void tearDown() {
		repository.deleteAll();
	}

}
