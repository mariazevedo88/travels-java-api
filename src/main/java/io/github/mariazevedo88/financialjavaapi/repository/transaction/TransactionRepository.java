package io.github.mariazevedo88.financialjavaapi.repository.transaction;

import java.util.List;

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

	List<Transaction> findByNsu(String nsu);
	
}
