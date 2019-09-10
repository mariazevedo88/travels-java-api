package io.github.mariazevedo88.financialjavaapi.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.mariazevedo88.financialjavaapi.factory.TransactionFactory;
import io.github.mariazevedo88.financialjavaapi.factory.impl.TransactionFactoryImpl;
import io.github.mariazevedo88.financialjavaapi.model.Transaction;

/**
 * Service that implements methods related to a transaction.
 * 
 * @author Mariana Azevedo
 * @since 08/09/2019
 */
@Service
public class TransactionService {
	
	private TransactionFactory factory;
	private List<Transaction> transactions;
	
	/**
	 * Method to create TransactionFactory
	 * 
	 * @author Mariana Azevedo
	 * @since 09/09/2019
	 */
	public void createTransactionFactory() {
		if(factory == null) factory = new TransactionFactoryImpl();
	}
	
	/**
	 * Method to create the transaction list
	 * 
	 * @author Mariana Azevedo
	 * @since 09/09/2019
	 */
	public void createTransactionList() {
		if(transactions == null) transactions = new ArrayList<>();
	}
	
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
	private long parseId(JSONObject transaction) {
		return Long.valueOf((int) transaction.get("id"));
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
	private BigDecimal parseAmount(JSONObject transaction) {
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
	private LocalDateTime parseTransactionDate(JSONObject transaction) {
		var transactionDate = (String) transaction.get("transactionDate");
		return ZonedDateTime.parse(transactionDate).toLocalDateTime();
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
	public boolean isTransactionInFuture(Transaction transaction) {
		return transaction.getTransactionDate().isAfter(LocalDateTime.now());
	}
	
	/**
	 * Method to fullfil the Transaction object
	 * 
	 * @author Mariana Azevedo
	 * @since 09/09/2019
	 * 
	 * @param jsonTransaction
	 * @param transaction
	 */
	private void setTransactionValues(JSONObject jsonTransaction, Transaction transaction) {
		
		String autorizationNumber = (String) jsonTransaction.get("autorizationNumber");
		String nsu = (String) jsonTransaction.get("nsu");
		
		transaction.setId(jsonTransaction.get("id") != null ? parseId(jsonTransaction) : transaction.getId());
		transaction.setAmount(jsonTransaction.get("amount") != null ? parseAmount(jsonTransaction) 
				: transaction.getAmount());
		transaction.setTransactionDate(jsonTransaction.get("transactionDate") != null ? 
				parseTransactionDate(jsonTransaction) : transaction.getTransactionDate());
		transaction.setAutorizationNumber(autorizationNumber != null ? autorizationNumber : transaction.getAutorizationNumber());
		transaction.setNsu(nsu != null ? nsu : transaction.getNsu());
	}
	
	/**
	 * Method to create a transaction
	 * 
	 * @author Mariana Azevedo
	 * @since 08/09/2019
	 * 
	 * @param jsonTransaction
	 * @return Transaction
	 */
	public Transaction createTransaction(JSONObject jsonTransaction) {
		
		createTransactionFactory();
		
		Transaction transaction = factory.createTransaction();
		setTransactionValues(jsonTransaction, transaction);
		
		return transaction;
	}
	
	/**
	 * Method to update a transaction
	 * 
	 * @author Mariana Azevedo
	 * @since 09/09/2019
	 * 
	 * @param transaction
	 * @param jsonTransaction
	 * 
	 * @return Transaction
	 */
	public Transaction updateTransaction(Transaction transaction, JSONObject jsonTransaction) {
		
		setTransactionValues(jsonTransaction, transaction);
		return transaction;
	}

	/**
	 * Method that add a transaction
	 * 
	 * @author Mariana Azevedo
	 * @since 09/09/2019
	 * 
	 * @param transaction
	 */
	public void addTransaction(Transaction transaction) {
		createTransactionList();
		transactions.add(transaction);
	}

	/**
	 * Method that get all transactions
	 * 
	 * @author Mariana Azevedo
	 * @since 08/09/2019
	 * 
	 * @param transactions
	 * @return List
	 */
	public List<Transaction> find() {
		return transactions;
	}
	
	/**
	 * Method that get a transaction by id
	 * 
	 * @author Mariana Azevedo
	 * @since 08/09/2019
	 * 
	 * @param id
	 * @return Transaction
	 */
	public Transaction findById(long id) {
		return transactions.stream().filter(t -> id == t.getId()).collect(Collectors.toList()).get(0);
	}
	
	/**
	 * Method that deletes the transactions created
	 * 
	 * @author Mariana Azevedo
	 * @since 08/09/2019
	 * 
	 * @return boolean
	 */
	public boolean delete() {
		transactions.clear();
		return transactions.isEmpty();
	}

}
