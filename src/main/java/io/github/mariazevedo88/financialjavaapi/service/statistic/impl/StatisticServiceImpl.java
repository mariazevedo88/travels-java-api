package io.github.mariazevedo88.financialjavaapi.service.statistic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.mariazevedo88.financialjavaapi.model.statistic.Statistic;
import io.github.mariazevedo88.financialjavaapi.repository.statistic.StatisticRepository;
import io.github.mariazevedo88.financialjavaapi.service.statistic.StatisticService;

/**
 * Class that implements the statistic's service methods
 * 
 * @author Mariana Azevedo
 * @since 28/03/2020
 */
@Service
public class StatisticServiceImpl implements StatisticService{

	@Autowired
	private StatisticRepository repository;
	
	/**
	 * @see StatisticService#save(Statistic)
	 */
	@Override
	public Statistic save(Statistic statistic) {
		return repository.save(statistic);
	}

}
