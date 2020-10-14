package io.github.mariazevedo88.financialjavaapi.service.user.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.mariazevedo88.financialjavaapi.model.user.UserTransaction;
import io.github.mariazevedo88.financialjavaapi.repository.user.UserTransactionRepository;
import io.github.mariazevedo88.financialjavaapi.service.user.UserTransactionService;

/**
 * Class that implements the user's transaction service methods
 * 
 * @author Mariana Azevedo
 * @since 13/10/2020
 */
@Service
public class UserTransactionServiceImpl implements UserTransactionService {

	@Autowired
	UserTransactionRepository repository;
	
	/**
	 * @see UserTransactionService#save(UserTransaction)
	 */
	@Override
	public UserTransaction save(UserTransaction userTransaction) {
		return repository.save(userTransaction);
	}

	/**
	 * @see UserTransactionService#findByUserIdAndTransactionId(Long, Long)
	 */
	@Override
	public Optional<UserTransaction> findByUserIdAndTransactionId(Long user, Long transaction) {
		return repository.findByUserIdAndTransactionId(user, transaction);
	}

}
