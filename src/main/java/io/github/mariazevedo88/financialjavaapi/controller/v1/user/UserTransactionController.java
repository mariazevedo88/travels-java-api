package io.github.mariazevedo88.financialjavaapi.controller.v1.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.mariazevedo88.financialjavaapi.controller.v1.transaction.TransactionController;
import io.github.mariazevedo88.financialjavaapi.dto.model.user.UserTransactionDTO;
import io.github.mariazevedo88.financialjavaapi.dto.response.Response;
import io.github.mariazevedo88.financialjavaapi.model.user.UserTransaction;
import io.github.mariazevedo88.financialjavaapi.service.user.UserTransactionService;
import io.github.mariazevedo88.financialjavaapi.util.FinancialApiUtil;

/**
 * SpringBoot RestController that implements all API service end-points related to the 
 * transaction that the user's have.
 * 
 * @author Mariana Azevedo
 * @since 13/10/2020
 */
@RestController
@RequestMapping("/financial/v1/user-transaction")
public class UserTransactionController {

	@Autowired
	private UserTransactionService service;
	
	/**
	 * Method that creates an association with the user and a transaction in the Financial API.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/10/2020
	 * 
	 * @param dto
	 * @param result
	 * @return ResponseEntity with a Response<UserTransactionDTO> object and the HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 201 - Created: Everything worked as expected.
	 * 400 - Bad Request: The request was unacceptable, often due to missing a required parameter.
	 * 401 - Unauthorized: No valid API key provided.
	 * 403 - Forbidden: The API key doesn't have permissions to perform the request.
	 * 404 - Not Found: The requested resource doesn't exist.
	 * 409 - Conflict: The request conflicts with another request (perhaps due to using the same idempotent key).
	 * 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential back-off of your requests.
	 * 500, 502, 503, 504 - Server Errors: something went wrong on Goldgem's end (These are rare).
	 * 
	 */
	@PostMapping
	public ResponseEntity<Response<UserTransactionDTO>> create(@RequestHeader(value=FinancialApiUtil.HEADER_FINANCIAL_API_VERSION, 
		defaultValue="${api.version}") String apiVersion, @Valid @RequestBody UserTransactionDTO dto, BindingResult result) {
		
		Response<UserTransactionDTO> response = new Response<>();
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		UserTransaction userTransaction = service.save(dto.convertDTOToEntity());
		UserTransactionDTO userTransactionDTO = userTransaction.convertEntityToDTO();
		
		//Self link - User Transaction
		createSelfLink(userTransaction, userTransactionDTO);
		
		//Relationship link - User
		createUserRelLink(userTransaction, userTransactionDTO);
		
		//Relationship link - Transaction
		createTransactionRelLink(userTransaction, userTransactionDTO);
		
		response.setData(userTransactionDTO);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(FinancialApiUtil.HEADER_FINANCIAL_API_VERSION, apiVersion);
		
		return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
	}

	/**
	 * Method that creates a self link to UserTransaction object
	 * 
	 * @author Mariana Azevedo
	 * @since 13/10/2020
	 * 
	 * @param userTransaction
	 * @param userTransactionDTO
	 */
	private void createSelfLink(UserTransaction userTransaction, UserTransactionDTO userTransactionDTO) {
		Link selfLink = WebMvcLinkBuilder.linkTo(UserTransactionController.class).slash(userTransaction.getId()).withSelfRel();
		userTransactionDTO.add(selfLink);
	}
	
	/**
	 * Method that creates a relationship link to Transaction object
	 * 
	 * @author Mariana Azevedo
	 * @since 13/10/2020
	 * 
	 * @param userTransaction
	 * @param userTransactionDTO
	 */
	private void createTransactionRelLink(UserTransaction userTransaction, UserTransactionDTO userTransactionDTO) {
		Link relTransactionLink = WebMvcLinkBuilder.linkTo(TransactionController.class).slash(userTransaction.getTransaction().getId()).withRel("transaction");
		userTransactionDTO.add(relTransactionLink);
	}

	/**
	 * Method that creates a relationship link to User object
	 * 
	 * @author Mariana Azevedo
	 * @since 13/10/2020
	 * 
	 * @param userTransaction
	 * @param userTransactionDTO
	 */
	private void createUserRelLink(UserTransaction userTransaction, UserTransactionDTO userTransactionDTO) {
		Link relUserLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(userTransaction.getUser().getId()).withRel("user");
		userTransactionDTO.add(relUserLink);
	}
}
