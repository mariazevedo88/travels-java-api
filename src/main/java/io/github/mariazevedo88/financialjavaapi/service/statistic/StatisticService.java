package io.github.mariazevedo88.financialjavaapi.service.statistic;

import java.math.BigDecimal;
import java.util.Optional;

import io.github.mariazevedo88.financialjavaapi.model.statistic.Statistic;

/**
 * Service Interface that provides methods for manipulating Statistics objects.
 * 
 * @author Mariana Azevedo
 * @since 08/09/2019
 */
public interface StatisticService {
	
	Statistic save(Statistic statistic);
	
	Optional<Statistic> verifyIfStatisticsIsSame(BigDecimal sum, BigDecimal avg, BigDecimal max, 
			BigDecimal min, long count);
	
}
