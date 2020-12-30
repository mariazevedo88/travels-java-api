package io.github.mariazevedo88.travelsjavaapi.service.account.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.mariazevedo88.travelsjavaapi.model.account.Account;
import io.github.mariazevedo88.travelsjavaapi.repository.account.AccountRepository;
import io.github.mariazevedo88.travelsjavaapi.service.account.AccountService;

/**
 * Class that implements the account service methods
 * 
 * @author Mariana Azevedo
 * @since 31/10/2020
 */
@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	AccountRepository repository;

	/**
	 * @see AccountService#save(Account)
	 */
	@Override
	public Account save(Account account) {
		return repository.save(account);
	}

	/**
	 * @see AccountService#findByAccountNumber(String)
	 */
	@Override
	public Optional<Account> findByAccountNumber(String accountNumber) {
		return repository.findByAccountNumber(accountNumber);
	}

	/**
	 * @see AccountService#findById(Long)
	 */
	@Override
	public Optional<Account> findById(Long accountId) {
		return repository.findById(accountId);
	}

}
