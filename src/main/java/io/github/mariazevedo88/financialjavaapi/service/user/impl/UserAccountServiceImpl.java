package io.github.mariazevedo88.financialjavaapi.service.user.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.mariazevedo88.financialjavaapi.model.user.UserAccount;
import io.github.mariazevedo88.financialjavaapi.repository.user.UserAccountRepository;
import io.github.mariazevedo88.financialjavaapi.service.user.UserAccountService;

/**
 * Class that implements the user's transaction service methods
 * 
 * @author Mariana Azevedo
 * @since 13/10/2020
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {

	@Autowired
	UserAccountRepository repository;
	
	/**
	 * @see UserAccountService#save(UserAccount)
	 */
	@Override
	public UserAccount save(UserAccount userTransaction) {
		return repository.save(userTransaction);
	}

	/**
	 * @see UserAccountService#findByUserIdAndAccountId(Long, Long)
	 */
	@Override
	public Optional<UserAccount> findByUserIdAndAccountId(Long user, Long account) {
		return repository.findByUserIdAndAccountId(user, account);
	}

}
