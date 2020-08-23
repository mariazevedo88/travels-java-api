package io.github.mariazevedo88.financialjavaapi.service.statistic.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.mariazevedo88.financialjavaapi.exception.DuplicateStatisticsException;
import io.github.mariazevedo88.financialjavaapi.model.statistic.Statistic;
import io.github.mariazevedo88.financialjavaapi.repository.statistic.StatisticRepository;
import io.github.mariazevedo88.financialjavaapi.service.statistic.StatisticService;

/**
 * Class that implements the statistic's service methods
 * 
 * @author Mariana Azevedo
 * @since 28/03/2020
 */
@Service("statisticService")
public class StatisticServiceImpl implements StatisticService {

	private StatisticRepository statisticRepository;
	
	@Autowired
	public StatisticServiceImpl(StatisticRepository statisticRepository) {
		this.statisticRepository = statisticRepository;
	}
	
	/**
	 * @see StatisticService#save(Statistic)
	 */
	@Override
	public Statistic save(Statistic statistic) {
		return statisticRepository.save(statistic);
	}

	/**
	 * @see StatisticService#verifyIfStatisticsIsSame(BigDecimal, BigDecimal, BigDecimal, BigDecimal, long)
	 * @throws DuplicateStatisticsException 
	 */
	@Override
	public Statistic verifyIfStatisticsIsSame(BigDecimal sum, BigDecimal avg, BigDecimal max, 
			BigDecimal min, long count) throws DuplicateStatisticsException {
		return statisticRepository.verifyIfStatisticsIsSame(sum, avg, max, min, count).orElseThrow(() -> 
			new DuplicateStatisticsException("Already exists a statistic in the database with the same parameters."));
	}
	
}
