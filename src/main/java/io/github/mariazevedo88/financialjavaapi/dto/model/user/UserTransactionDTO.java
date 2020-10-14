package io.github.mariazevedo88.financialjavaapi.dto.model.user;

import javax.validation.constraints.NotNull;

import org.springframework.hateoas.RepresentationModel;

import io.github.mariazevedo88.financialjavaapi.model.transaction.Transaction;
import io.github.mariazevedo88.financialjavaapi.model.user.User;
import io.github.mariazevedo88.financialjavaapi.model.user.UserTransaction;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Class that implements User Transaction data transfer object (DTO)
 * 
 * @author Mariana Azevedo
 * @since 13/10/2020
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class UserTransactionDTO extends RepresentationModel<UserTransactionDTO>{

	private Long id;
	
	@NotNull(message = "Id cannot be null")
	private Long user;
	
	@NotNull(message = "Transaction Id cannot be null")
	private Long transaction;
	
	/**
	 * Method to convert an User Transaction DTO to an User Transaction entity.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/10/2020
	 * 
	 * @return an UserTransaction object
	 */
	public UserTransaction convertDTOToEntity() {
		
		User user = new User();
		user.setId(this.getUser());
		
		Transaction transaction = new Transaction();
		transaction.setId(this.getTransaction());
		
		UserTransaction userTransaction = new UserTransaction();
		userTransaction.setId(this.getId());
		userTransaction.setUser(user);
		userTransaction.setTransaction(transaction);
		
		return userTransaction;
	}
	
}
