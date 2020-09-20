package io.github.mariazevedo88.financialjavaapi.service.transaction.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.zero_x_baadf00d.partialize.Partialize;

import io.github.mariazevedo88.financialjavaapi.dto.model.transaction.TransactionDTO;
import io.github.mariazevedo88.financialjavaapi.exception.TransactionNotFoundException;
import io.github.mariazevedo88.financialjavaapi.model.transaction.Transaction;
import io.github.mariazevedo88.financialjavaapi.repository.transaction.TransactionRepository;
import io.github.mariazevedo88.financialjavaapi.service.transaction.TransactionService;

/**
 * Class that implements the transaction's service methods
 * 
 * @author Mariana Azevedo
 * @since 28/03/2020
 */
@Service
public class TransactionServiceImpl implements TransactionService {
	
	TransactionRepository transactionRepository;
	
	@Autowired
	public TransactionServiceImpl(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	/**
	 * @see TransactionService#save(Transaction)
	 */
	@Override
	public Transaction save(Transaction transaction) {
		return transactionRepository.save(transaction);
	}

	/**
	 * @see TransactionService#findByNsu(String)
	 */
	@Override
	@Cacheable(value="transactionNsuCache", key="#nsu", unless="#result==null")
	public List<Transaction> findByNsu(String nsu) {
		return transactionRepository.findByNsu(nsu);
	}

	/**
	 * @see TransactionService#deleteById(Long)
	 */
	@Override
	public void deleteById(Long transactionId) {
		transactionRepository.deleteById(transactionId);		
	}

	/**
	 * @see TransactionService#findById(Long)
	 * @throws TransactionNotFoundException 
	 */
	@Override
	@Cacheable(value="transactionIdCache", key="#id")
	public Transaction findById(Long id) throws TransactionNotFoundException {
		return transactionRepository.findById(id).orElseThrow(() -> 
			new TransactionNotFoundException("Transaction id=" + id + " not found"));
	}

	/**
	 * @see TransactionService#findBetweenDates(LocalDateTime, LocalDateTime, Pageable)
	 */
	@Override
	public Page<Transaction> findBetweenDates(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
		return transactionRepository.
				findAllByTransactionDateGreaterThanEqualAndTransactionDateLessThanEqual(startDate, 
					endDate, pageable);
	}

	/**
	 * @see TransactionService#findAll()
	 */
	@Override
	public List<Transaction> findAll() {
		return transactionRepository.findAll();
	}

	/**
	 * @see TransactionService#getPartialJsonResponse(String, TransactionDTO)
	 */
	@Override
	public TransactionDTO getPartialJsonResponse(String fields, TransactionDTO dto) {
		
		final Partialize partialize = new Partialize();
		final ContainerNode<?> node = partialize.buildPartialObject(fields, TransactionDTO.class, dto);
		return new ObjectMapper().convertValue(node, TransactionDTO.class);
	}

}
