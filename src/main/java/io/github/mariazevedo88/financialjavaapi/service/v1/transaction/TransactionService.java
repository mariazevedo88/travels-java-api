package io.github.mariazevedo88.financialjavaapi.service.v1.transaction;

import java.util.List;
import java.util.Optional;

import io.github.mariazevedo88.financialjavaapi.model.v1.transaction.Transaction;

/**
 * Service Interface that provides methods for manipulating Transaction objects.
 * 
 * @author Mariana Azevedo
 * @since 08/09/2019
 */
public interface TransactionService {
	
	Transaction save(Transaction transaction);
	
	void deleteById(Long transactionId);
	
	Optional<Transaction> findById(Long id);
	
	List<Transaction> findByNsu(String nsu);
	
	List<Transaction> findAll();

}
