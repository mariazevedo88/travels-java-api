package io.github.mariazevedo88.financialjavaapi.ut;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.mariazevedo88.financialjavaapi.model.Statistic;
import io.github.mariazevedo88.financialjavaapi.model.Transaction;
import io.github.mariazevedo88.financialjavaapi.service.StatisticService;
import io.github.mariazevedo88.financialjavaapi.service.TransactionService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TransactionService.class, StatisticService.class })
@EnableConfigurationProperties
public class FinancialJavaApiUnitTests {
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private StatisticService statisticService;
	
	@Before
	public void setUp() {
		transactionService.createFactory();
		transactionService.createTransactionList();
	}

	@Test
	public void shouldReturnNotNullTransactionService() {
		assertNotNull(transactionService);
	}

	@Test
	public void shouldReturnNotNullStatisticService() throws Exception {
		assertNotNull(statisticService);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void shouldReturnTransactionCreatedWithSuccess() throws Exception {
		
		LocalDateTime now = LocalDateTime.now();
		String localDate = now.toString().concat("Z");
		
		JSONObject json = new JSONObject();
		json.put("id", 1);
		json.put("nsu", "220788");
		json.put("autorizationNumber", "010203");
		json.put("amount", "22.88");
		json.put("transactionDate", localDate);
		
		Transaction transaction = transactionService.create(json);
		
		assertNotNull(transaction);
		assertEquals(transaction.getId().intValue(), json.get("id"));
		assertEquals(transaction.getNsu(), json.get("nsu"));
		assertEquals(transaction.getAutorizationNumber(), json.get("autorizationNumber"));
		assertEquals(transaction.getAmount().toString(), json.get("amount"));
		assertEquals(localDate, json.get("transactionDate"));
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void shouldReturnTransactionCreatedInFuture() throws Exception {
		
		JSONObject json = new JSONObject();
		json.put("id", 2);
		json.put("nsu", "220788");
		json.put("autorizationNumber", "010203");
		json.put("amount", "22.88");
		json.put("transactionDate", "2020-09-11T09:59:51.312Z");
		
		Transaction transaction = transactionService.create(json);
		boolean transactionInFuture = transactionService.isTransactionInFuture(transaction);
		
		assertTrue(transactionInFuture);
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void shouldReturnTransactionStatisticsCalculated() throws Exception {
		
		transactionService.delete();
		
		LocalDateTime now = LocalDateTime.now();
		String localDate = now.toString().concat("Z");
		
		JSONObject json1 = new JSONObject();
		json1.put("id", 1);
		json1.put("nsu", "220788");
		json1.put("autorizationNumber", "010203");
		json1.put("amount", "22.88");
		json1.put("transactionDate", localDate);
		
		Transaction transaction = transactionService.create(json1);
		transactionService.add(transaction);
		
		JSONObject json = new JSONObject();
		json.put("id", 2);
		json.put("nsu", "300691");
		json.put("autorizationNumber", "040506");
		json.put("amount", "120.0");
		json.put("transactionDate", "2020-09-11T09:59:51.312Z");
		
		transaction = transactionService.create(json);
		transactionService.add(transaction);
		
		Statistic statistic = statisticService.create(transactionService.find());
		
		assertNotNull(statistic);
		assertEquals("142.88", statistic.getSum().toString());
		assertEquals("71.44", statistic.getAvg().toString());
		assertEquals("22.88", statistic.getMin().toString());
		assertEquals("120.00", statistic.getMax().toString());
		assertEquals(2, statistic.getCount());
	}
	
	@After
	public void tearDown() {
		transactionService.clearObjects();
	}

}
