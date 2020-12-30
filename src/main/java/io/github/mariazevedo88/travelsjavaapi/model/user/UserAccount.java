package io.github.mariazevedo88.travelsjavaapi.model.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;

import io.github.mariazevedo88.travelsjavaapi.dto.model.user.UserAccountDTO;
import io.github.mariazevedo88.travelsjavaapi.model.account.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class that implements an UserAccount entity - to represents
 * an User and Account relationship in the Travels Java API.
 * 
 * @author Mariana Azevedo
 * @since 12/10/2020
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users_accounts")
public class UserAccount implements Serializable {

	private static final long serialVersionUID = 7536502811640528298L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Account account;
	
	/**
	 * Method to convert an User Transaction entity to an User Transaction DTO.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/10/2020
	 * 
	 * @param userTransaction
	 * @return an UserTransactionDTO object
	 */
	public UserAccountDTO convertEntityToDTO() {
		return new ModelMapper().map(this, UserAccountDTO.class);
	}

}

 