package io.github.mariazevedo88.financialjavaapi.controller.v1.transaction;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.mariazevedo88.financialjavaapi.dto.model.transaction.TransactionDTO;
import io.github.mariazevedo88.financialjavaapi.dto.response.Response;
import io.github.mariazevedo88.financialjavaapi.exception.NotParsableContentException;
import io.github.mariazevedo88.financialjavaapi.exception.TransactionInvalidUpdateException;
import io.github.mariazevedo88.financialjavaapi.exception.TransactionNotFoundException;
import io.github.mariazevedo88.financialjavaapi.model.transaction.Transaction;
import io.github.mariazevedo88.financialjavaapi.service.transaction.TransactionService;
import io.github.mariazevedo88.financialjavaapi.util.FinancialApiUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

/**
 * SpringBoot RestController that creates all service end-points related to the transaction.
 * 
 * @author Mariana Azevedo
 * @since 08/09/2019
 */
@Log4j2
@RestController
@RequestMapping("/financial/v1/transactions")
public class TransactionController {
	
	TransactionService transactionService;
	
	@Autowired
	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}
	
	/**
	 * Method that creates a transaction in the database.
	 * 
	 * @author Mariana Azevedo
	 * @since 02/04/2020
	 * 
	 * @param apiVersion - API version at the moment
	 * @param apiKey - API Key to access the routes
	 * @param dto, where: id - transaction id; nsu - identification number of a sales transaction using cards. May be null if transaction was paid in cash;
	 * authorizationNumber - is a one-time code used in the processing of online transactions; amount – transaction amount; a string of arbitrary length that is 
	 * parsable as a BigDecimal; transactionDate – transaction time in the ISO 8601 format YYYY-MM-DDThh:mm:ss.sssZ in the Local timezone; type - transaction type: 
	 * CARD (credit-card) or MONEY (paid in cash). 
	 * @param result - Bind result
	 * 
	 * @return ResponseEntity with a <code>Response<TransactionDTO></code> object and the HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 201 - Created: Everything worked as expected.
	 * 400 - Bad Request: The request was unacceptable, often due to missing a required parameter.
	 * 404 - Not Found: The requested resource doesn't exist.
	 * 409 - Conflict: The request conflicts with another request (perhaps due to using the same idempotent key).
	 * 422 – Unprocessable Entity: if any of the fields are not parsable or the transaction date is in the future.
	 * 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential back-off of your requests.
	 * 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).
	 * 
	 * @throws NotParsableContentException
	 * @throws BadRequestException 
	 */
	@PostMapping
	@ApiOperation(value = "Route to create a transaction")
	public ResponseEntity<Response<TransactionDTO>> create(@RequestHeader(value=FinancialApiUtil.HEADER_FINANCIAL_API_VERSION, defaultValue="${api.version}") String apiVersion, 
			@RequestHeader(value=FinancialApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, @Valid @RequestBody TransactionDTO dto, BindingResult result) 
					throws NotParsableContentException {
		
		Response<TransactionDTO> response = new Response<>();

		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		if(FinancialApiUtil.isTransactionDTOInFuture(dto)) {
			throw new NotParsableContentException("Date of the transaction is in the future.");
		}
		
		Transaction transaction = dto.convertDTOToEntity(); 
		Transaction transactionToCreate = transactionService.save(transaction);

		TransactionDTO dtoSaved = transactionToCreate.convertEntityToDTO();
		createSelfLink(transactionToCreate, dtoSaved);

		response.setData(dtoSaved);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(FinancialApiUtil.HEADER_FINANCIAL_API_VERSION, apiVersion);
		headers.add(FinancialApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
	}
	
	/**
	 * Method that updates a transaction in the database.
	 * 
	 * @author Mariana Azevedo
	 * @since 02/04/2020
	 * 
	 * @param apiVersion - API version at the moment
	 * @param apiKey - API Key to access the routes
	 * @param dto, where: id - transaction id; NSU - identification number of a sales transaction using cards. May be null if transaction was paid in cash;
	 * autorizationNumber - is a one-time code used in the processing of online transactions; amount – transaction amount; a string of arbitrary length that is 
	 * parsable as a BigDecimal; transactionDate – transaction time in the ISO 8601 format YYYY-MM-DDThh:mm:ss.sssZ in the Local timezone; type - transaction type: 
	 * CARD (credit-card) or MONEY (paid in cash).
	 * @param result - Bind result
	 * 
	 * @return ResponseEntity with a <code>Response<TransactionDTO></code> object and the HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Everything worked as expected.
	 * 400 - Bad Request: The request was unacceptable, often due to missing a required parameter.
	 * 404 - Not Found: The requested resource doesn't exist.
	 * 409 - Conflict: The request conflicts with another request (perhaps due to using the same idempotent key).
	 * 422 – Unprocessable Entity: if any of the fields are not parsable or the transaction date is in the future.
	 * 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential back-off of your requests.
	 * 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).
	 * 
	 * @throws TransactionNotFoundException
	 * @throws TransactionInvalidUpdateException
	 * @throws NotParsableContentException 
	 */
	@PutMapping(path = "/{id}")
	@ApiOperation(value = "Route to update a transaction")
	public ResponseEntity<Response<TransactionDTO>> update(@RequestHeader(value=FinancialApiUtil.HEADER_FINANCIAL_API_VERSION, defaultValue="${api.version}") String apiVersion, 
		@RequestHeader(value=FinancialApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, @Valid @RequestBody TransactionDTO dto, BindingResult result) 
		throws TransactionNotFoundException, TransactionInvalidUpdateException, NotParsableContentException {
		
		Response<TransactionDTO> response = new Response<>();

		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		if(FinancialApiUtil.isTransactionDTOInFuture(dto)) {
			throw new NotParsableContentException("Date of the transaction is in the future.");
		}

		Transaction transactionToFind = transactionService.findById(dto.getId());
		if (transactionToFind.getId().compareTo(dto.getId()) != 0) {
			throw new TransactionInvalidUpdateException("You don't have permission to change the transaction id=" + dto.getId());
		}

		Transaction transaction = dto.convertDTOToEntity();
		Transaction transactionToUpdate = transactionService.save(transaction);
		
		TransactionDTO itemDTO = transactionToUpdate.convertEntityToDTO();
		createSelfLink(transactionToUpdate, itemDTO);
		response.setData(itemDTO);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(FinancialApiUtil.HEADER_FINANCIAL_API_VERSION, apiVersion);
		headers.add(FinancialApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}

	/**
	 * Method that search for all the transactions saved in a period of time.
	 *  
	 * @author Mariana Azevedo
	 * @since 02/04/2020
	 * 
	 * @param apiVersion - API version at the moment
	 * @param apiKey - API Key to access the routes
	 * @param startDate - the start date of the search
	 * @param endDate - the end date of the search
	 * @param pageable Object for pagination information: the page that will be return in the search, 
	 * the size of page, and sort direction that the results should be shown: ASC - ascending order; 
	 * DESC - descending order.
	 * 
	 * @return ResponseEntity with a <code>Response<Page<TransactionDTO>></code> object and the HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Everything worked as expected.
	 * 404 - Not Found: The requested resource doesn't exist.
	 * 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential back-off of your requests.
	 * 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).
	 * 
	 * @throws TransactionNotFoundException 
	 */
	@GetMapping
	@ApiOperation(value = "Route to find all transactions of the API in a period of time")
	public ResponseEntity<Response<Page<TransactionDTO>>> findAllBetweenDates(@RequestHeader(value=FinancialApiUtil.HEADER_FINANCIAL_API_VERSION, defaultValue="${api.version}") 
		String apiVersion, @RequestHeader(value=FinancialApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") 
	    LocalDate startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate, @PageableDefault(page = 1, size = 10, sort = {"id"}) Pageable pageable) 
	    		throws TransactionNotFoundException {
		
		Response<Page<TransactionDTO>> response = new Response<>();
		
		LocalDateTime startDateTime = FinancialApiUtil.convertLocalDateToLocalDateTime(startDate);
		LocalDateTime endDateTime = FinancialApiUtil.convertLocalDateToLocalDateTime(endDate);
		
		Page<Transaction> transactions = transactionService.findBetweenDates(startDateTime, endDateTime, pageable);
		
		if (transactions.isEmpty()) {
			throw new TransactionNotFoundException("There are no transactions registered between startDate=" + startDate 
					+ " and endDate=" + endDate);
		}
		
		Page<TransactionDTO> itemsDTO = transactions.map(t -> t.convertEntityToDTO());
		itemsDTO.stream().forEach(dto -> {
			try {
				createSelfLinkInCollections(apiVersion, apiKey, dto);
			} catch (TransactionNotFoundException e) {
				log.error("There are no transactions registered between startDate= {} and endDate= {}", startDate, endDate);
			}
		});
		
		response.setData(itemsDTO);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(FinancialApiUtil.HEADER_FINANCIAL_API_VERSION, apiVersion);
		headers.add(FinancialApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}

	/**
	 * Method that search for all the transactions given a NSU.
	 * 
	 * @author Mariana Azevedo
	 * @since 02/04/2020
	 * 
	 * @param apiVersion - API version at the moment
	 * @param apiKey - API Key to access the routes
	 * @param transactionNSU - the NSU of the transaction
	 * 
	 * @return ResponseEntity with a <code>Response<String></code> object and the HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Everything worked as expected.
	 * 400 - Bad Request: The request was unacceptable, often due to missing a required parameter.
	 * 404 - Not Found: The requested resource doesn't exist.
	 * 409 - Conflict: The request conflicts with another request (perhaps due to using the same idempotent key).
	 * 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential backoff of your requests.
	 * 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).
	 * 
	 * @throws TransactionNotFoundException
	 */
	@GetMapping(value = "/byNsu/{nsu}")
	@ApiOperation(value = "Route to find transactions by the NSU in the API")
	public ResponseEntity<Response<List<TransactionDTO>>> findByNsu(@RequestHeader(value=FinancialApiUtil.HEADER_FINANCIAL_API_VERSION, defaultValue="${api.version}") 
		String apiVersion, @RequestHeader(value=FinancialApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, 
		@PathVariable("nsu") String transactionNSU) throws TransactionNotFoundException {
		
		Response<List<TransactionDTO>> response = new Response<>();
		List<Transaction> transactions = transactionService.findByNsu(transactionNSU);
		
		if (transactions.isEmpty()) {
			throw new TransactionNotFoundException("There are no transactions registered with the nsu=" + transactionNSU);
		}
		
		List<TransactionDTO> transactionsDTO = new ArrayList<>();
		transactions.stream().forEach(t -> transactionsDTO.add(t.convertEntityToDTO()));
		
		transactionsDTO.stream().forEach(dto -> {
			try {
				createSelfLinkInCollections(apiVersion, apiKey, dto);
			} catch (TransactionNotFoundException e) {
				log.error("There are no transactions registered with the nsu= {}", transactionNSU);
			}
		});
		
		response.setData(transactionsDTO);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(FinancialApiUtil.HEADER_FINANCIAL_API_VERSION, apiVersion);
		headers.add(FinancialApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}
	
	/**
	 * Method that search a transactions by the id.
	 * 
	 * @param apiVersion - API version at the moment
	 * @param apiKey - API Key to access the routes
	 * @param transactionId - the id of the transaction
	 * @param fields - Transaction fields that should be returned in JSON as Partial Response
	 * 
	 * @return ResponseEntity with a <code>Response<TransactionDTO></code> object and the HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Everything worked as expected.
	 * 400 - Bad Request: The request was unacceptable, often due to missing a required parameter.
	 * 404 - Not Found: The requested resource doesn't exist.
	 * 409 - Conflict: The request conflicts with another request (perhaps due to using the same idempotent key).
	 * 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential back-off of your requests.
	 * 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).
	 * 
	 * @throws TransactionNotFoundException
	 */
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Route to find a transaction by your id in the API")
	public ResponseEntity<Response<TransactionDTO>> findById(@RequestHeader(value=FinancialApiUtil.HEADER_FINANCIAL_API_VERSION, defaultValue="${api.version}") 
		String apiVersion, @RequestHeader(value=FinancialApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, @PathVariable("id") Long transactionId,
		@RequestParam(required = false) String fields) throws TransactionNotFoundException {
		
		Response<TransactionDTO> response = new Response<>();
		Transaction transaction = transactionService.findById(transactionId);
		
		TransactionDTO dto = transaction.convertEntityToDTO();
		
		if(fields != null) {
			dto = transactionService.getPartialJsonResponse(fields, dto);
		}
		
		createSelfLink(transaction, dto);
		response.setData(dto);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(FinancialApiUtil.HEADER_FINANCIAL_API_VERSION, apiVersion);
		headers.add(FinancialApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}
	
	/**
	 * Method that delete an transaction.
	 * 
	 * @author Mariana Azevedo
	 * @since 02/04/2020
	 * 
	 * @param apiVersion - API version at the moment
	 * @param apiKey - API Key to access the routes
	 * @param transactionId - the id of the transaction
	 * 
	 * @return ResponseEntity with a <code>Response<String></code> object and the HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 204 - OK: Everything worked as expected.
	 * 400 - Bad Request: The request was unacceptable, often due to missing a required parameter.
	 * 404 - Not Found: The requested resource doesn't exist.
	 * 409 - Conflict: The request conflicts with another request (perhaps due to using the same idempotent key).
	 * 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential back-off of your requests.
	 * 500, 502, 503, 504 - Server Errors: something went wrong on Goldgem's end (These are rare).
	 * 
	 * @throws TransactionNotFoundException 
	 */
	@DeleteMapping(value = "/{id}")
	@ApiOperation(value = "Route to delete a transaction in the API")
	public ResponseEntity<Response<String>> delete(@RequestHeader(value=FinancialApiUtil.HEADER_FINANCIAL_API_VERSION, defaultValue="${api.version}") 
		String apiVersion, @RequestHeader(value=FinancialApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, 
		@PathVariable("id") Long transactionId) throws TransactionNotFoundException {
		
		Response<String> response = new Response<>();
		Transaction transaction = transactionService.findById(transactionId);
		
		transactionService.deleteById(transaction.getId());
		response.setData("Transaction id=" + transaction.getId() + " successfully deleted");
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(FinancialApiUtil.HEADER_FINANCIAL_API_VERSION, apiVersion);
		headers.add(FinancialApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Method that creates a self link to transaction object
	 * 
	 * @author Mariana Azevedo
	 * @since 03/04/2020
	 * 
	 * @param transaction
	 * @param transactionDTO
	 */
	private void createSelfLink(Transaction transaction, TransactionDTO transactionDTO) {
		Link selfLink = WebMvcLinkBuilder.linkTo(TransactionController.class).slash(transaction.getId()).withSelfRel();
		transactionDTO.add(selfLink);
	}
	
	/**
	 * Method that creates a self link in a collection of transactions
	 * 
	 * @author Mariana Azevedo
	 * @since 03/04/2020
	 * 
	 * @param apiVersion - API version at the moment
	 * @param apiKey - API Key to access the routes
	 * @param transactionDTO
	 * @throws TransactionNotFoundException
	 */
	private void createSelfLinkInCollections(String apiVersion, String apiKey, final TransactionDTO transactionDTO) 
			throws TransactionNotFoundException {
		Link selfLink = linkTo(methodOn(TransactionController.class).findById(apiVersion, apiKey, transactionDTO.getId(), null))
				.withSelfRel().expand();
		transactionDTO.add(selfLink);
	}
	
}
