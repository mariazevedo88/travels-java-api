package io.github.mariazevedo88.financialjavaapi.service.transaction;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.mariazevedo88.financialjavaapi.dto.model.transaction.TransactionDTO;
import io.github.mariazevedo88.financialjavaapi.exception.TransactionNotFoundException;
import io.github.mariazevedo88.financialjavaapi.model.transaction.Transaction;

/**
 * Service Interface that provides methods for manipulating Transaction objects.
 * 
 * @author Mariana Azevedo
 * @since 08/09/2019
 */
public interface TransactionService {
	
	/**
	 * Method that save a transaction.
	 * 
	 * @author Mariana Azevedo
	 * @since 08/09/2019
	 * 
	 * @param transaction
	 * @return <code>Transaction</code> object
	 */
	Transaction save(Transaction transaction);
	
	/**
	 * Method that remove a transaction by an id.
	 * 
	 * @author Mariana Azevedo
	 * @since 08/09/2019
	 * 
	 * @param transactionId
	 */
	void deleteById(Long transactionId);
	
	/**
	 * Method that find a transaction by an id.
	 * 
	 * @author Mariana Azevedo
	 * @since 08/09/2019
	 * 
	 * @param id
	 * @return <code>Optional<Transaction></code> object
	 */
	Transaction findById(Long id) throws TransactionNotFoundException;
	
	/**
	 * Method that find one or more transactions by a nsu.
	 * 
	 * @author Mariana Azevedo
	 * @since 08/09/2019
	 * 
	 * @param nsu
	 * @return <code>List<Transaction></code> object
	 */
	List<Transaction> findByNsu(String nsu);
	
	/**
	 * Method that find all transactions.
	 * 
	 * @author Mariana Azevedo
	 * @since 08/09/2019
	 * 
	 * @return <code>List<Transaction></code> object
	 */
	List<Transaction> findAll();
	
	/**
	 * Method that find all transactions in a period of time.
	 * 
	 * @author Mariana Azevedo
	 * @since 21/08/2020
	 * 
	 * @param startDate - the start date of the search
	 * @param endDate - the end date of the search
	 * @param pageable - object for pagination information: the page that will be return in the search, 
	 * the size of page, and sort direction that the results should be shown: ASC - ascending order; 
	 * DESC - descending order.
	 * 
	 * @return <code>Page<Transaction></code> object
	 */
	Page<Transaction> findBetweenDates(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
	
	/**
	 * Method to build a partial response in requests regarding Transaction.
	 * 
	 * @author Mariana Azevedo
	 * @since 17/09/2020
	 * 
	 * @param fields
	 * @param dto
	 * @return a <code>TransactionDTO</code> object
	 */
	TransactionDTO getPartialJsonResponse(String fields, TransactionDTO dto);
}
