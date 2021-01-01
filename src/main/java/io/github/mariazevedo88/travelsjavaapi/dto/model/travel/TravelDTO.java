package io.github.mariazevedo88.travelsjavaapi.dto.model.travel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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

import io.github.mariazevedo88.travelsjavaapi.model.travel.Travel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class that implements Travel data transfer object (DTO)
 * 
 * @author Mariana Azevedo
 * @since 01/04/2020
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Partialize(allowedFields = {"id", "orderNumber", "startDate", "endDate", "amount", "account", "type"},
		defaultFields = {"orderNumber", "startDate", "amount"})
public class TravelDTO extends RepresentationModel<TravelDTO> {
	
	private Long id;
	
	@NotNull(message="Order Number cannot be null")
	@Length(min=6, message="Order Number must contain at least 6 characters")
	private String orderNumber;

	@NotNull(message="Start Date cannot be null")
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", locale = "en-US", timezone = "Brazil/East")
	private LocalDateTime startDate;
	
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", locale = "en-US", timezone = "Brazil/East")
	private LocalDateTime endDate;
	
	@NotNull(message="Amount cannot be null")
	private BigDecimal amount;
	
	@NotNull(message="Type cannot be null")
	@Pattern(regexp="^(ONE-WAY|RETURN|MULTI-CITY)$", 
		message="For the type only the values ONE-WAY, RETURN or MULTI-CITY are accepted.")
	private String type;
	
	@NotNull(message="Account Id cannot be null")
	private Long accountId;
	
	/**
	 * Method to convert an Travel DTO to a Travel entity.
	 * 
	 * @author Mariana Azevedo
	 * @since 03/04/2020
	 * 
	 * @param dto
	 * @return a <code>Travel</code> object
	 */
	public Travel convertDTOToEntity() {
		return new ModelMapper().map(this, Travel.class);
	}

}
