package io.github.mariazevedo88.financialjavaapi.dto.model.statistic;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.springframework.hateoas.RepresentationModel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class that implements Statistic data transfer object (DTO)
 * 
 * @author Mariana Azevedo
 * @since 01/04/2020
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class StatisticDTO extends RepresentationModel<StatisticDTO> {

	private Long id;
	
	@NotNull(message="Sum cannot be null")
	private BigDecimal sum;
	
	@NotNull(message="Avg cannot be null")
	private BigDecimal avg;
	
	@NotNull(message="Max cannot be null")
	private BigDecimal max;
	
	@NotNull(message="Min cannot be null")
	private BigDecimal min;
	
	@NotNull(message="Count cannot be null")
	private long count;
}
