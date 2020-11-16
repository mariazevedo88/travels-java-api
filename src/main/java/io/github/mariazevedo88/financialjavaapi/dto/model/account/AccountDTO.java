package io.github.mariazevedo88.financialjavaapi.dto.model.account;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import io.github.mariazevedo88.financialjavaapi.enumeration.AccountTypeEnum;
import io.github.mariazevedo88.financialjavaapi.model.account.Account;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@EqualsAndHashCode(callSuper = false)
public class AccountDTO extends RepresentationModel<AccountDTO> {

	@Getter
	private Long id;
	
	@Getter
	@NotNull(message = "Account Number cannot be null.")
	@Length(min=6, message="Account Number must contain at least 6 numbers.")
	private Long accountNumber;
	
	@Getter
	@NotNull(message="The Account type cannot be null.")
	@Pattern(regexp="^(CHECKING_ACCOUNT|SAVINGS_ACCOUNT)$", 
		message="For the account type only the values CHECKING_ACCOUNT or SAVINGS_ACCOUNT are accepted.")
	private AccountTypeEnum accountType; 
	
	/**
	 * Method to convert an Account DTO to a Account entity.
	 * 
	 * @author Mariana Azevedo
	 * @since 31/10/2020
	 * 
	 * @param dto
	 * @return a <code>Account</code> object
	 */
	public Account convertDTOToEntity() {
		return new ModelMapper().map(this, Account.class);
	}
}
