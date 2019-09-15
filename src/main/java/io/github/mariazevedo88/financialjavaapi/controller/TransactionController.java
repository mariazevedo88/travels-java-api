package io.github.mariazevedo88.financialjavaapi.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.mariazevedo88.financialjavaapi.exception.InvalidJSONException;
import io.github.mariazevedo88.financialjavaapi.exception.NotParsableContentException;
import io.github.mariazevedo88.financialjavaapi.exception.TransactionNotFoundException;
import io.github.mariazevedo88.financialjavaapi.model.Transaction;
import io.github.mariazevedo88.financialjavaapi.service.TransactionService;

/**
 * SpringBoot RestController that creates all service endpoints related to the transaction.
 * 
 * @author Mariana Azevedo
 * @since 09/09/2019
 */
@RestController
@RequestMapping(path = "/financial/v1")
public class TransactionController {
	
	private static final Logger logger = Logger.getLogger(TransactionController.class);
	
	@Autowired
	private TransactionService transactionService;
	
	/**
	 * Method that list all transactions
	 * 
	 * @author Mariana Azevedo
	 * @since 09/09/2019
	 * 
	 * @return ResponseEntity - 200, if has transactions or 404 if hasn't.
	 */
	@GetMapping(path = "/transactions", produces = { "application/json" })
	public ResponseEntity<List<Transaction>> find() throws TransactionNotFoundException{
		
		if(transactionService.find().isEmpty()) {
			throw new TransactionNotFoundException("There are no transactions registered in the application.");
		}
		
		logger.info(transactionService.find());
		return ResponseEntity.ok(transactionService.find());
	}
	
	/**
	 * Method that deletes all existing transactions.
	 * 
	 * @author Mariana Azevedo
	 * @since 09/09/2019
	 * 
	 * @return ResponseEntity - 204, if delete with success or 205 if hasn't.
	 */
	@DeleteMapping(path = "/transactions")
	public ResponseEntity<Boolean> delete(){
		transactionService.delete();
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * Method that creates a transaction.
	 * 
	 * @author Mariana Azevedo
	 * @since 09/09/2019
	 * 
	 * @param transaction, where: id - transaction id; nsu - identification number of a sales transaction using cards. May be null if transaction was paid in cash;
	 * autorizationNumber - is a one-time code used in the processing of online transactions; amount – transaction amount; a string of arbitrary length that is 
	 * parsable as a BigDecimal; transactionDate – transaction time in the ISO 8601 format YYYY-MM-DDThh:mm:ss.sssZ in the Local timezone; type - transaction type: 
	 * CARD (credit-card) or MONEY (paid in cash).
	 * 
	 * @return Returns an empty body with one of the following:
	 * 201 – in case of success
	 * 400 – if the JSON is invalid
	 * 422 – if any of the fields are not parsable or the transaction date is in the future
	 * 
	 * @throws InvalidJSONException 
	 * @throws NotParsableContentException 
	 */
	@PostMapping(path = "/transactions", produces = { "application/json" })
	public ResponseEntity<Transaction> create(@RequestBody JSONObject transaction) throws InvalidJSONException, NotParsableContentException {
		try {
			
			if(!transactionService.isJSONValid(transaction.toString())) {
				throw new InvalidJSONException("Invalid JSON.");
			}
			
			Transaction transactionCreated = transactionService.create(transaction);
			var uri = ServletUriComponentsBuilder.fromCurrentRequest().path(transactionCreated.getId().toString())
					.build().toUri();
			transactionService.setTransactionLinks(uri.toString(), transactionCreated);
			
			if(transactionService.isTransactionInFuture(transactionCreated)) {
				throw new NotParsableContentException("The transaction date is in the future.");
			}
			
			transactionService.add(transactionCreated);
			
			logger.info(transactionCreated);
			
			return ResponseEntity.created(uri).body(null);
			
		}catch(Exception e) {
			throw new NotParsableContentException(e.getMessage());
		}
	}
	
	/**
	 * Method that creates a transaction.
	 * 
	 * @author Mariana Azevedo
	 * @since 09/09/2019
	 * 
	 * @param transaction, where: id - transaction id; nsu - identification number of a sales transaction using cards. May be null if transaction was paid in cash;
	 * autorizationNumber - is a one-time code used in the processing of online transactions; amount – transaction amount; a string of arbitrary length that is 
	 * parsable as a BigDecimal; transactionDate – transaction time in the ISO 8601 format YYYY-MM-DDThh:mm:ss.sssZ in the Local timezone; type - transaction type: 
	 * CARD (credit-card) or MONEY (paid in cash).
	 * 
	 * @return Returns an empty body with one of the following:
	 * 201 – in case of success
	 * 400 – if the JSON is invalid
	 * 422 – if any of the fields are not parsable or the transaction date is in the future
	 */
	@PutMapping(path = "/transactions/{id}", produces = { "application/json" })
	public ResponseEntity<Transaction> update(@PathVariable("id") long id, @RequestBody JSONObject transaction) throws
		InvalidJSONException, NotParsableContentException, TransactionNotFoundException {
		
		try {
			
			if(!transactionService.isJSONValid(transaction.toString())) {
				throw new InvalidJSONException("Invalid JSON.");
			}
			
			Transaction transactionToUpdate = transactionService.findById(id);
			if(transactionToUpdate == null) {
				throw new TransactionNotFoundException("There are no transactions registered in the application.");
			}
			
			if(transactionService.isTransactionInFuture(transactionToUpdate)) {
				throw new NotParsableContentException("The transaction date is in the future.");
			}
			
			Transaction transactionUpdated = transactionService.update(transactionToUpdate, transaction);

			logger.info(transactionUpdated);

			return ResponseEntity.ok(transactionUpdated);
			
		}catch(Exception e) {
			throw new NotParsableContentException(e.getMessage());
		}
	}

}
