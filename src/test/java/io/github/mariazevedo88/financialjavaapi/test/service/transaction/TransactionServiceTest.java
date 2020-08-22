package io.github.mariazevedo88.financialjavaapi.test.service.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import io.github.mariazevedo88.financialjavaapi.model.enumeration.TransactionTypeEnum;
import io.github.mariazevedo88.financialjavaapi.model.transaction.Transaction;
import io.github.mariazevedo88.financialjavaapi.repository.transaction.TransactionRepository;
import io.github.mariazevedo88.financialjavaapi.service.transaction.TransactionService;

/**
 * Class that implements tests of the TransactionService features.
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
	
	private static final LocalDateTime DATE = LocalDateTime.now();
	
	/**
	 * Method to setup a Transaction to use in the tests.
	 * 
	 * @author Mariana Azevedo
	 * @since 05/04/2020
	 */
	@Test
	public void testSave() {
		
		BDDMockito.given(repository.save(Mockito.any(Transaction.class)))
			.willReturn(getMockTransaction());
		Transaction response = service.save(new Transaction());
		
		assertNotNull(response);
		assertEquals("123456", response.getNsu());
	}
	
	/**
	 * Method that test the service that search for an Transaction by the nsu.
	 * 
	 * @author Mariana Azevedo
	 * @since 05/04/2020
	 */
	@Test
	public void testFindByNsu() {
		
		BDDMockito.given(repository.findByNsu(Mockito.anyString()))
				.willReturn(Collections.singletonList(new Transaction()));
		
		List<Transaction> response = service.findByNsu("123456");
		assertTrue(!response.isEmpty());
	}
	
	@Test
	public void testFindBetweenDates() {
		
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(getMockTransaction());
		Page<Transaction> page = new PageImpl<>(transactions);
		
		BDDMockito.given(repository.findAllByTransactionDateGreaterThanEqualAndTransactionDateLessThanEqual(Mockito.any(LocalDateTime.class), 
				Mockito.any(LocalDateTime.class), Mockito.any(PageRequest.class))).willReturn(page);
		
		Page<Transaction> response = service.findBetweenDates(DATE, DATE, 0);
		assertNotNull(response);
	}
	
	/**
	 * Method that fill a mock of a Wallet and WalletItem to use as return in the tests.
	 * 
	 * @author Mariana Azevedo
	 * @since 24/03/2020
	 * 
	 * @return <code>WalletItem</code> object
	 */
	private Transaction getMockTransaction() {
		
		Transaction transaction = new Transaction(1L, "123456", "010203",
				DATE, BigDecimal.valueOf(288), TransactionTypeEnum.CARD);
		return transaction;
	}

}
