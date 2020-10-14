package io.github.mariazevedo88.financialjavaapi.model.user;

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

import io.github.mariazevedo88.financialjavaapi.dto.model.user.UserTransactionDTO;
import io.github.mariazevedo88.financialjavaapi.model.transaction.Transaction;
import lombok.Getter;
import lombok.Setter;

/**
 * Class that implements an UserTransaction entity - to represents
 * an User and Transaction relationship in the Financial API.
 * 
 * @author Mariana Azevedo
 * @since 12/10/2020
 */
@Entity
@Getter
@Setter
@Table(name = "users_financial_transaction")
public class UserTransaction implements Serializable {

	private static final long serialVersionUID = 7536502811640528298L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JoinColumn(name = "financial_user", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	@JoinColumn(name = "financial_transaction", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Transaction transaction;
	
	/**
	 * Method to convert an User Transaction entity to an User Transaction DTO.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/10/2020
	 * 
	 * @param userTransaction
	 * @return an UserTransactionDTO object
	 */
	public UserTransactionDTO convertEntityToDTO() {
		return new ModelMapper().map(this, UserTransactionDTO.class);
	}

}

 