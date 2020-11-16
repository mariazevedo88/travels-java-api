package io.github.mariazevedo88.financialjavaapi.dto.model.user;

import javax.validation.constraints.NotNull;

import org.springframework.hateoas.RepresentationModel;

import io.github.mariazevedo88.financialjavaapi.model.account.Account;
import io.github.mariazevedo88.financialjavaapi.model.user.User;
import io.github.mariazevedo88.financialjavaapi.model.user.UserAccount;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Class that implements User Account data transfer object (DTO)
 * 
 * @author Mariana Azevedo
 * @since 13/10/2020
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class UserAccountDTO extends RepresentationModel<UserAccountDTO>{

	private Long id;
	
	@NotNull(message = "Id cannot be null")
	private Long user;
	
	@NotNull(message = "Account Id cannot be null")
	private Long account;
	
	/**
	 * Method to convert an User Account DTO to an User Account entity.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/10/2020
	 * 
	 * @return an UserAccount object
	 */
	public UserAccount convertDTOToEntity() {
		
		User user = new User();
		user.setId(this.getUser());
		
		Account account = new Account();
		account.setId(this.getAccount());
		
		UserAccount userAccount = new UserAccount();
		userAccount.setId(this.getId());
		userAccount.setUser(user);
		userAccount.setAccount(account);
		
		return userAccount;
	}
	
}
