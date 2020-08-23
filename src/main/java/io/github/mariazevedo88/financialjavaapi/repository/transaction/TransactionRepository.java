package io.github.mariazevedo88.financialjavaapi.repository.transaction;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.mariazevedo88.financialjavaapi.model.transaction.Transaction;

/**
 * Interface that implements the Transaction Repository, with JPA CRUD methods
 * and other customized searches.
 *  
 * @author Mariana Azevedo
 * @since 28/03/2020
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	/**
	 * Method to search for all the transactions in the API in a period of time.
	 * 
	 * @author Mariana Azevedo
	 * @since 21/08/2020
	 * 
	 * @return <code>Page<Transaction></code> object
	 */
	Page<Transaction> findAllByTransactionDateGreaterThanEqualAndTransactionDateLessThanEqual
		(LocalDateTime startDate, LocalDateTime endDate, Pageable pg);
	
	/**
	 * Method to search for all the transactions in the same NSU (unique sequential number).
	 * 
	 * @author Mariana Azevedo
	 * @since 28/03/2020
	 * 
	 * @return <code>List<Transaction></code> object
	 */
	List<Transaction> findByNsu(String nsu);
	
}
