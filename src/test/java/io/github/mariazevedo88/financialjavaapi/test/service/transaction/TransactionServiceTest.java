package io.github.mariazevedo88.financialjavaapi.test.service.transaction;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import io.github.mariazevedo88.financialjavaapi.model.v1.transaction.Transaction;
import io.github.mariazevedo88.financialjavaapi.repository.transaction.TransactionRepository;
import io.github.mariazevedo88.financialjavaapi.service.v1.transaction.TransactionService;

/**
 * Class that implements tests of the TransactionService funcionalities.
 * 
 * @author Mariana Azevedo
 * @since 05/04/2020
 */
@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class TransactionServiceTest {
	
	@Autowired
	private TransactionService service;

	@MockBean
	private TransactionRepository repository;
	
	/**
	 * Method to setup a Transaction to use in the tests.
	 * 
	 * @author Mariana Azevedo
	 * @since 05/04/2020
	 */
	@BeforeAll
	public void setUp() {
		
		BDDMockito.given(repository.findByNsu(Mockito.anyString()))
			.willReturn(Collections.singletonList(new Transaction()));
	}
	
	/**
	 * Method that test the service that search for an Transaction by the nsu.
	 * 
	 * @author Mariana Azevedo
	 * @since 05/04/2020
	 */
	@Test
	public void testFindByNsu() {
		List<Transaction> transactions = service.findByNsu("123456");
		assertTrue(!transactions.isEmpty());
	}

}
