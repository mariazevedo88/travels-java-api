package io.github.mariazevedo88.financialjavaapi.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class that implements the Statistic structure.
 * 
 * @author Mariana Azevedo
 * @since 08/09/2019
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Statistic {
	
	private BigDecimal sum;
	private BigDecimal avg;
	private BigDecimal max;
	private BigDecimal min;
	private long count;

}
