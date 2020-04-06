package io.github.mariazevedo88.financialjavaapi.service.statistic;

import io.github.mariazevedo88.financialjavaapi.model.statistic.Statistic;

/**
 * Service Interface that provides methods for manipulating Statistics objects.
 * 
 * @author Mariana Azevedo
 * @since 08/09/2019
 */
public interface StatisticService {
	
	Statistic save(Statistic statistic);
	
}
