package io.github.mariazevedo88.financialjavaapi.dto.model.v1.statistic;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.springframework.hateoas.RepresentationModel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Class that implements Statistic data transfer object (DTO)
 * 
 * @author Mariana Azevedo
 * @since 01/04/2020
 */
@EqualsAndHashCode(callSuper = false)
public class StatisticDTO extends RepresentationModel<StatisticDTO> {

	@Getter
	@Setter
	@NotNull(message="Sum cannot be null")
	private BigDecimal sum;
	
	@Getter
	@Setter
	@NotNull(message="Avg cannot be null")
	private BigDecimal avg;
	
	@Getter
	@Setter
	@NotNull(message="Max cannot be null")
	private BigDecimal max;
	
	@Getter
	@Setter
	@NotNull(message="Min cannot be null")
	private BigDecimal min;
	
	@Getter
	@Setter
	@NotNull(message="Count cannot be null")
	private long count;
}
