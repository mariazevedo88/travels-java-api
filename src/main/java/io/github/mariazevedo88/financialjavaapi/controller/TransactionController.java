package io.github.mariazevedo88.financialjavaapi.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.mariazevedo88.financialjavaapi.model.Transaction;
import io.github.mariazevedo88.financialjavaapi.service.TransactionService;

/**
 * SpringBoot RestController that creates all service endpoints related to the transaction.
 * 
 * @author Mariana Azevedo
 * @since 09/09/2019
 */
@RestController
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
	@GetMapping(path = "/transactions")
	public ResponseEntity<List<Transaction>> find() {
		if(transactionService.find().isEmpty()) {
			return ResponseEntity.notFound().build(); 
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
	public ResponseEntity<Boolean> delete() {
		try {
			transactionService.delete();
			return ResponseEntity.noContent().build();
		}catch(Exception e) {
			logger.error(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
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
	@PostMapping(path = "/transactions")
	@ResponseBody
	public ResponseEntity<Transaction> create(@RequestBody JSONObject transaction) {
		try {
			if(transactionService.isJSONValid(transaction.toString())) {
				Transaction transactionCreated = transactionService.create(transaction);
				var uri = ServletUriComponentsBuilder.fromCurrentRequest().path(transactionCreated.getNsu()).build().toUri();
				
				if(transactionService.isTransactionInFuture(transactionCreated)){
					logger.error("The transaction date is in the future.");
					return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
				}else {
					transactionService.add(transactionCreated);
					return ResponseEntity.created(uri).body(null);
				}
			}else {
				return ResponseEntity.badRequest().body(null);
			}
		}catch(Exception e) {
			logger.error("JSON fields are not parsable. " + e);
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
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
	public ResponseEntity<Transaction> create(@PathVariable("id") long id, @RequestBody JSONObject transaction) {
		try {
			if(transactionService.isJSONValid(transaction.toString())) {
				Transaction transactionToUpdate = transactionService.findById(id);
				if(transactionToUpdate == null){
					logger.error("The transaction not found.");
					return ResponseEntity.notFound().build(); 
				}else {
					Transaction transactionUpdated = transactionService.update(transactionToUpdate, transaction);
					return ResponseEntity.ok(transactionUpdated);
				}
			}else {
				return ResponseEntity.badRequest().body(null);
			}
		}catch(Exception e) {
			logger.error("JSON fields are not parsable." + e);
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

}
