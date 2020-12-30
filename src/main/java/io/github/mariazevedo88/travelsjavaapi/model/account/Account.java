package io.github.mariazevedo88.travelsjavaapi.model.account;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import io.github.mariazevedo88.travelsjavaapi.dto.model.account.AccountDTO;
import io.github.mariazevedo88.travelsjavaapi.enumeration.AccountTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Class that implements the Account structure.
 * 
 * @author Mariana Azevedo
 * @since 16/12/2020
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class Account implements Serializable {

	private static final long serialVersionUID = -6762432601286928295L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "account_number")
	private String accountNumber;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "account_type")
	private AccountTypeEnum accountType; 
	
	public Account(Long id) {
		this.id = id;
	}

	/**
	 * Method to convert an Account entity to a Account DTO.
	 * 
	 * @author Mariana Azevedo
	 * @since 31/10/2020
	 * 
	 * @param account
	 * @return a <code>AccountDTO</code> object
	 */
	public AccountDTO convertEntityToDTO() {
		return new ModelMapper().map(this, AccountDTO.class);
	}
}
