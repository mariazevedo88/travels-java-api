package io.github.mariazevedo88.financialjavaapi.dto.model.v1.transaction;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.github.mariazevedo88.financialjavaapi.model.enumeration.TransactionTypeEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Class that implements Transaction data transfer object (DTO)
 * 
 * @author Mariana Azevedo
 * @since 01/04/2020
 */
@EqualsAndHashCode(callSuper = false)
public class TransactionDTO extends RepresentationModel<TransactionDTO> {
	
	@Getter
	@Setter
	private Long id;
	
	@Getter
	@Setter
	@NotNull(message="Nsu cannot be null")
	@Length(min=6, message="Nsu must contain at least 6 characters")
	private String nsu;

	@Getter
	@Setter
	private String authorizationNumber;
	
	@Getter
	@Setter
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	@NotNull(message="TransactionDate cannot be null")
	private Date transactionDate;
	
	@Getter
	@Setter
	@NotNull(message="Amount cannot be null")
	private BigDecimal amount;
	
	@Getter
	@Setter
	@NotNull(message="Type cannot be null")
	private TransactionTypeEnum type;

}
