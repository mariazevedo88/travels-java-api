package io.github.mariazevedo88.financialjavaapi.controller.v1.statistic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.mariazevedo88.financialjavaapi.dto.model.statistic.StatisticDTO;
import io.github.mariazevedo88.financialjavaapi.dto.response.Response;
import io.github.mariazevedo88.financialjavaapi.model.statistic.Statistic;
import io.github.mariazevedo88.financialjavaapi.model.transaction.Transaction;
import io.github.mariazevedo88.financialjavaapi.service.statistic.StatisticService;
import io.github.mariazevedo88.financialjavaapi.service.transaction.TransactionService;
import io.github.mariazevedo88.financialjavaapi.util.FinancialApiUtil;
import io.swagger.annotations.ApiOperation;

/**
 * SpringBoot RestController that creates all service end-points related to the statistics.
 * 
 * @author Mariana Azevedo
 * @since 09/09/2019
 */
@RestController
@RequestMapping("/financial/v1/statistics")
public class StatisticController {
	
	private StatisticService statisticService;
	
	private TransactionService transactionService;
	
	@Autowired
	public StatisticController(StatisticService statisticService, TransactionService transactionService) {
		this.statisticService = statisticService;
		this.transactionService = transactionService;
	}
	
	/**
	 * Method that creates statistics based on the transactions.
	 * 
	 * @author Mariana Azevedo
	 * @since 03/04/2020
	 * 
	 * @param apiVersion
	 * @param apiKey
	 * 
	 * @return ResponseEntity with a Response<StatisticDTO> object and the HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 201 - Created: Everything worked as expected.
	 * 400 - Bad Request: The request was unacceptable, often due to missing a required parameter.
	 * 404 - Not Found: The requested resource doesn't exist.
	 * 409 - Conflict: The request conflicts with another request (perhaps due to using the same idempotent key).
	 * 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential backoff of your requests.
	 * 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).
	 */
	@GetMapping
	@ApiOperation(value = "Route to create statistics in the API")
	public ResponseEntity<Response<StatisticDTO>> create(@RequestHeader(value=FinancialApiUtil.HEADER_FINANCIAL_API_VERSION, 
		defaultValue="${api.version}") String apiVersion, @RequestHeader(value=FinancialApiUtil.HEADER_API_KEY, 
		defaultValue="${api.key}") String apiKey) {
		
		Response<StatisticDTO> response = new Response<>();

		Statistic statistics = createStatistics(transactionService.findAll());
		Statistic statisticsToCreate = statisticService.save(statistics);

		StatisticDTO dto = statisticsToCreate.convertEntityToDTO();
		createSelfLink(statisticsToCreate, dto);
		response.setData(dto);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(FinancialApiUtil.HEADER_FINANCIAL_API_VERSION, apiVersion);
		headers.add(FinancialApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
		
	}

	/**
	 * Method that creates the statistics by the list of transactions in the database.
	 * 
	 * @author Mariana Azevedo
	 * @since 03/04/2020
	 * 
	 * @param transactions
	 * @return a <code>Statistic</code> object
	 */
	private Statistic createStatistics(List<Transaction> transactions) {
		
		BigDecimal sum = BigDecimal.valueOf(transactions.stream().mapToDouble(t -> t.getAmount().doubleValue()).sum())
			.setScale(2, RoundingMode.HALF_UP);
		
		BigDecimal avg = BigDecimal.valueOf(transactions.stream().mapToDouble(t -> t.getAmount().doubleValue()).average().orElse(0.0))
			.setScale(2, RoundingMode.HALF_UP);
				
		BigDecimal min = BigDecimal.valueOf(transactions.stream().mapToDouble(t -> t.getAmount().doubleValue()).min().orElse(0.0))
			.setScale(2, RoundingMode.HALF_UP);
		
		BigDecimal max = BigDecimal.valueOf(transactions.stream().mapToDouble(t -> t.getAmount().doubleValue()).max().orElse(0.0))
			.setScale(2, RoundingMode.HALF_UP);
		
		long count = transactions.stream().count();
		return new Statistic(sum, avg, min, max, count);
	}
	
	/**
	 * Method that creates a self link to statistic object
	 * 
	 * @author Mariana Azevedo
	 * @since 03/04/2020
	 * 
	 * @param statistics
	 * @param statisticDTO
	 */
	private void createSelfLink(Statistic statistics, StatisticDTO statisticDTO) {
		Link selfLink = WebMvcLinkBuilder.linkTo(StatisticController.class).slash(statistics.getId()).withSelfRel();
		statisticDTO.add(selfLink);
	}
	
}
