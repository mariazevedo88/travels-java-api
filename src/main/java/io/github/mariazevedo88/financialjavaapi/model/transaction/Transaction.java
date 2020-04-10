package io.github.mariazevedo88.financialjavaapi.model.transaction;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

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
@Getter
@Setter
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
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date transactionDate;
	
	@NotNull
	private BigDecimal amount;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private TransactionTypeEnum type;
	
	public Transaction (TransactionTypeEnum type){
		this.type = type;
	}
	
}
