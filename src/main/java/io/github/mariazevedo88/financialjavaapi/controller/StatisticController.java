package io.github.mariazevedo88.financialjavaapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.mariazevedo88.financialjavaapi.model.Statistic;
import io.github.mariazevedo88.financialjavaapi.model.Transaction;
import io.github.mariazevedo88.financialjavaapi.service.StatisticService;
import io.github.mariazevedo88.financialjavaapi.service.TransactionService;

/**
 * SpringBoot RestController that creates all service endpoints related to the statistics.
 * 
 * @author Mariana Azevedo
 * @since 09/09/2019
 */
@RestController
public class StatisticController {
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private StatisticService statisticsService;
	
	
	/**
	 * Method that returns the statistics based on the transactions
	 * 
	 * @author Mariana Azevedo
	 * @since 09/09/2019
	 * 
	 * @return ResponseEntity - 200
	 */
	@GetMapping(path = "/statistics", produces = { "application/json" })
	public ResponseEntity<Statistic> getStatistics() {
		List<Transaction> transactions = transactionService.find();
		Statistic statistics = statisticsService.create(transactions);
		return ResponseEntity.ok(statistics);
	}

}
