package io.github.mariazevedo88.financialjavaapi.dto.model.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.zero_x_baadf00d.partialize.annotation.Partialize;

import io.github.mariazevedo88.financialjavaapi.model.enumeration.TransactionTypeEnum;
import io.github.mariazevedo88.financialjavaapi.model.transaction.Transaction;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class that implements Transaction data transfer object (DTO)
 * 
 * @author Mariana Azevedo
 * @since 01/04/2020
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Partialize(allowedFields = {"id", "nsu", "authorizationNumber", "transactionDate", "amount", "type"},
		defaultFields = {"nsu", "transactionDate", "amount"})
public class TransactionDTO extends RepresentationModel<TransactionDTO> {
	
	private Long id;
	
	@NotNull(message="Nsu cannot be null")
	@Length(min=6, message="Nsu must contain at least 6 characters")
	private String nsu;

	private String authorizationNumber;
	
	@NotNull(message="TransactionDate cannot be null")
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", locale = "en-US", timezone = "Brazil/East")
	private LocalDateTime transactionDate;
	
	@NotNull(message="Amount cannot be null")
	private BigDecimal amount;
	
	@NotNull(message="Type cannot be null")
	private TransactionTypeEnum type;
	
	/**
	 * Method to convert an Transaction DTO to a Transaction entity.
	 * 
	 * @author Mariana Azevedo
	 * @since 03/04/2020
	 * 
	 * @param dto
	 * @return a <code>Transaction</code> object
	 */
	public Transaction convertDTOToEntity() {
		return new ModelMapper().map(this, Transaction.class);
	}

}
