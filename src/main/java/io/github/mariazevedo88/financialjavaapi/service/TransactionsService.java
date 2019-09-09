package io.github.mariazevedo88.financialjavaapi.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.mariazevedo88.financialjavaapi.model.Transaction;

/**
 * Service that implements methods related to a transaction.
 * 
 * @author Mariana Azevedo
 * @since 08/09/2019
 */
@Service
public class TransactionsService {
	
	/**
	 * Method that check if JSON is valid.
	 * 
	 * @author Mariana Azevedo
	 * @since 08/09/2019
	 * 
	 * @param jsonInString
	 * @return boolean
	 */
	public boolean isJSONValid(String jsonInString) {
	    try {
	       final var mapper = new ObjectMapper();
	       mapper.readTree(jsonInString);
	       return true;
	    } catch (IOException e) {
	       return false;
	    }
	}
	
	/**
	 * Method to parse the id field.
	 * 
	 * @author Mariana Azevedo
	 * @since 08/09/2019
	 * 
	 * @param transaction
	 * @return long
	 */
	public long parseId(JSONObject transaction) {
		return (Long) transaction.get("id");
	}
	
	/**
	 * Method to parse the amount field.
	 * 
	 * @author Mariana Azevedo
	 * @since 08/09/2019
	 * 
	 * @param transaction
	 * @return BigDecimal
	 */
	public BigDecimal parseAmount(JSONObject transaction) {
		return new BigDecimal((String) transaction.get("amount"));
	}
	
	/**
	 * Method to parse the transactionDate field.
	 * 
	 * @author Mariana Azevedo
	 * @since 08/09/2019
	 * 
	 * @param transaction
	 * @return LocalDateTime
	 */
	public LocalDateTime parseTimestamp(JSONObject transaction) {
		var transactionDate = (String) transaction.get("transactionDate");
		var zdt = ZonedDateTime.parse(transactionDate);
		return zdt.toLocalDateTime();
	}
	
	/**
	 * Method that check if the transaction is being created in the future.
	 * 
	 * @author Mariana Azevedo
	 * @since 08/09/2019
	 * 
	 * @param transaction
	 * @return boolean
	 */
	public boolean isFutureTransaction(Transaction transaction) {
		return transaction.getTransactionDate().isAfter(LocalDateTime.now());
	}
	
	/**
	 * Method that checks transactions
	 * 
	 * @author Mariana Azevedo
	 * @since 08/09/2019
	 * 
	 * @param transactions
	 * @return List
	 */
	public List<Transaction> getAllTransactions(List<Transaction> transactions) {
		return transactions.stream().collect(Collectors.toList());
	}
	
	/**
	 * Method that checks transactions at the last minute
	 * 
	 * @author Mariana Azevedo
	 * @since 08/09/2019
	 * 
	 * @param transactions
	 * @return List
	 */
	public List<Transaction> getAllTransactionsLastMinute(List<Transaction> transactions) {
		return transactions.stream().filter(t -> Duration.between(t.getTransactionDate(), LocalDateTime.now())
				.toMillis() <= 60000).collect(Collectors.toList());
	}

}
