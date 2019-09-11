package io.github.mariazevedo88.financialjavaapi.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class that implements the Transaction structure.
 * 
 * @author Mariana Azevedo
 * @since 08/09/2019
 * 
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transaction {
	
	public enum TransactionTypeEnum { CARD, MONEY } 

	private Long id;
	private String nsu;
	private String autorizationNumber;
	private LocalDateTime transactionDate;
	private BigDecimal amount;
	private TransactionTypeEnum type;
	
	public Transaction(TransactionTypeEnum type){
		this.type = type;
	}
	
}
