package io.github.mariazevedo88.financialjavaapi.service.statistic;

import java.math.BigDecimal;

import io.github.mariazevedo88.financialjavaapi.exception.DuplicateStatisticsException;
import io.github.mariazevedo88.financialjavaapi.model.statistic.Statistic;

/**
 * Interface that provides methods for manipulating Statistics objects.
 * 
 * @author Mariana Azevedo
 * @since 08/09/2019
 */
public interface StatisticService {
	
	/**
	 * Method that saves a statistic.
	 * 
	 * @author Mariana Azevedo
	 * @since 08/06/2020
	 * 
	 * @param statistic
	 * @return <code>Statistic</code> object
	 */
	Statistic save(Statistic statistic);
	
	/**
	 * Method that verifies if the statistics already exists in the database.
	 * 
	 * @author Mariana Azevedo
	 * @since 11/06/2020
	 * 
	 * @param sum
	 * @param avg
	 * @param max
	 * @param min
	 * @param count
	 * @return <code>Optional<Statistic></code> object
	 */
	Statistic verifyIfStatisticsIsSame(BigDecimal sum, BigDecimal avg, BigDecimal max, 
			BigDecimal min, long count) throws DuplicateStatisticsException;
	
}
