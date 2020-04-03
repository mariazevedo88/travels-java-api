package io.github.mariazevedo88.financialjavaapi.repository.transaction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.mariazevedo88.financialjavaapi.model.v1.transaction.Transaction;

/**
 * Interface that implements the Transaction Repository, with JPA CRUD methods
 * and other customized searches.
 *  
 * @author Mariana Azevedo
 * @since 28/03/2020
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	List<Transaction> findByNsu(String nsu);
	
}
