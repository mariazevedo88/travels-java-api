package io.github.mariazevedo88.financialjavaapi.dto.model.v1.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.RepresentationModel;

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
	private String autorizationNumber;
	
	@Getter
	@Setter
	@NotNull(message="TransactionDate cannot be null")
	private LocalDateTime transactionDate;
	
	@Getter
	@Setter
	@NotNull(message="Amount cannot be null")
	private BigDecimal amount;
	
	@Getter
	@Setter
	@NotNull(message="Type cannot be null")
	private TransactionTypeEnum type;

}
