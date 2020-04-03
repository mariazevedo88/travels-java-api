package io.github.mariazevedo88.financialjavaapi.model.v1.transaction;

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

import io.github.mariazevedo88.financialjavaapi.model.enumeration.TransactionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class that implements the Transaction structure.
 * 
 * @author Mariana Azevedo
 * @since 01/04/2020
 */
@Entity
@Table(name = "transaction")
@NoArgsConstructor
@AllArgsConstructor
public class Transaction implements Serializable {
	
	private static final long serialVersionUID = -3656431259068389491L;
	
	@Id
	@Getter
	@Setter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Getter
	@Setter
	private String nsu;
	
	@Getter
	@Setter
	private String autorizationNumber;
	
	@Getter
	@Setter
	private LocalDateTime transactionDate;
	
	@Getter
	@Setter
	private BigDecimal amount;
	
	@Getter
	@Setter
	@Enumerated(EnumType.STRING)
	private TransactionTypeEnum type;
	
	public Transaction (TransactionTypeEnum type){
		this.type = type;
	}
	
}
