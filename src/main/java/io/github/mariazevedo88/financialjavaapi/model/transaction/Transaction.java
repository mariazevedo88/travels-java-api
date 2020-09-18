package io.github.mariazevedo88.financialjavaapi.model.transaction;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import io.github.mariazevedo88.financialjavaapi.dto.model.transaction.TransactionDTO;
import io.github.mariazevedo88.financialjavaapi.model.enumeration.TransactionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Class that implements the Transaction structure.
 * 
 * @author Mariana Azevedo
 * @since 01/04/2020
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
public class Transaction implements Serializable {
	
	private static final long serialVersionUID = -3656431259068389491L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String nsu;
	
	private String authorizationNumber;
	
	@NotNull
	private LocalDateTime transactionDate;
	
	@NotNull
	private BigDecimal amount;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private TransactionTypeEnum type;
	
	public Transaction (TransactionTypeEnum type){
		this.type = type;
	}
	
	/**
	 * Method to convert an Transaction entity to a Transaction DTO.
	 * 
	 * @author Mariana Azevedo
	 * @since 03/04/2020
	 * 
	 * @param transaction
	 * @return a <code>TransactionDTO</code> object
	 */
	public TransactionDTO convertEntityToDTO() {
		return new ModelMapper().map(this, TransactionDTO.class);
	}
	
}
