package io.github.mariazevedo88.travelsjavaapi.controller.v1.account;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.mariazevedo88.travelsjavaapi.dto.model.account.AccountDTO;
import io.github.mariazevedo88.travelsjavaapi.dto.response.Response;
import io.github.mariazevedo88.travelsjavaapi.exception.AccountNotFoundException;
import io.github.mariazevedo88.travelsjavaapi.exception.TravelNotFoundException;
import io.github.mariazevedo88.travelsjavaapi.model.account.Account;
import io.github.mariazevedo88.travelsjavaapi.service.account.AccountService;
import io.github.mariazevedo88.travelsjavaapi.util.TravelsApiUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

/**
 * SpringBoot RestController that creates all service end-points related to the account.
 * 
 * @author Mariana Azevedo
 * @since 31/10/2020
 */
@Log4j2
@RestController
@RequestMapping("/api-travels/v1/accounts")
public class AccountController {

	AccountService accountService;
	
	@Autowired
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	/**
	 * Method that creates a account in the Travels Java API.
	 * 
	 * @author Mariana Azevedo
	 * @since 31/10/2020
	 * 
	 * @param dto
	 * @param result
	 * @return ResponseEntity with a Response<AccountDTO> object and the HTTP status
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
	 * 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).
	 */
	@PostMapping
	public ResponseEntity<Response<AccountDTO>> create(@RequestHeader(value=TravelsApiUtil.HEADER_TRAVELS_API_VERSION, 
		defaultValue="${api.version}") String apiVersion, @Valid @RequestBody AccountDTO dto, BindingResult result) {
		
		Response<AccountDTO> response = new Response<>();
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		Account account = accountService.save(dto.convertDTOToEntity());
		AccountDTO accountDTO = account.convertEntityToDTO();
		
		//Self link
		createSelfLink(account, accountDTO);
		response.setData(accountDTO);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(TravelsApiUtil.HEADER_TRAVELS_API_VERSION, apiVersion);
		
		return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
	}
	
	/**
	 * Method that search for all the account given an account number.
	 * 
	 * @author Mariana Azevedo
	 * @since 31/10/2020
	 * 
	 * @param apiVersion - API version at the moment
	 * @param apiKey - API Key to access the routes
	 * @param accountNumber - the account's unique number
	 * 
	 * @return ResponseEntity with a <code>Response<String></code> object and the HTTP status
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
	 * @throws TravelNotFoundException
	 */
	@GetMapping(value = "/byAccountNumber/{number}")
	@ApiOperation(value = "Route to find account by the Account Number in the API")
	public ResponseEntity<Response<List<AccountDTO>>> findByAccountNumber(@RequestHeader(value=TravelsApiUtil.HEADER_TRAVELS_API_VERSION, defaultValue="${api.version}") 
		String apiVersion, @RequestHeader(value=TravelsApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, 
		@PathVariable("number") String accountNumber) throws AccountNotFoundException {
		
		Response<List<AccountDTO>> response = new Response<>();
		Optional<Account> accounts = accountService.findByAccountNumber(accountNumber);
		
		if (accounts.isEmpty()) {
			throw new AccountNotFoundException("There are no accounts registered with the accountNumber=" + accountNumber);
		}
		
		List<AccountDTO> accountDTO = new ArrayList<>();
		accounts.stream().forEach(t -> accountDTO.add(t.convertEntityToDTO()));
		
		accountDTO.stream().forEach(dto -> {
			try {
				createSelfLinkInCollections(apiVersion, apiKey, dto);
			} catch (AccountNotFoundException e) {
				log.error("There are no accounts registered with the accountNumber= {}", accountNumber);
			}
		});
		
		response.setData(accountDTO);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(TravelsApiUtil.HEADER_TRAVELS_API_VERSION, apiVersion);
		headers.add(TravelsApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}
	
	/**
	 * Method that search a account by the id.
	 * 
	 * @param apiVersion - API version at the moment
	 * @param apiKey - API Key to access the routes
	 * @param accountId - the id of the account
	 * 
	 * @return ResponseEntity with a <code>Response<AccountDTO></code> object and the HTTP status
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
	 * @throws TravelNotFoundException
	 */
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Route to find a account by your id in the API")
	public ResponseEntity<Response<AccountDTO>> findById(@RequestHeader(value=TravelsApiUtil.HEADER_TRAVELS_API_VERSION, 
		defaultValue="${api.version}") String apiVersion, @RequestHeader(value=TravelsApiUtil.HEADER_API_KEY, defaultValue="${api.key}") 
		String apiKey, @PathVariable("id") Long accountId) throws AccountNotFoundException {
		
		Response<AccountDTO> response = new Response<>();
		Optional<Account> account = accountService.findById(accountId);
		
		Account accountFound = null;
		if(account.isPresent()) {
			accountFound = account.get();
		}
		
		AccountDTO dto = accountFound.convertEntityToDTO();
		
		createSelfLink(accountFound, dto);
		response.setData(dto);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(TravelsApiUtil.HEADER_TRAVELS_API_VERSION, apiVersion);
		headers.add(TravelsApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}
	
	/**
	 * Method that creates a self link to account object
	 * 
	 * @author Mariana Azevedo
	 * @since 31/10/2020
	 * 
	 * @param account
	 * @param accountDTO
	 */
	private void createSelfLink(Account account, AccountDTO accountDTO) {
		Link selfLink = WebMvcLinkBuilder.linkTo(AccountController.class).slash(account.getId()).withSelfRel();
		accountDTO.add(selfLink);
	}
	
	/**
	 * Method that creates a self link in a collection of accounts
	 * 
	 * @author Mariana Azevedo
	 * @since 31/10/2020
	 * 
	 * @param apiVersion - API version at the moment
	 * @param apiKey - API Key to access the routes
	 * @param accountDTO
	 * @throws AccountNotFoundException
	 */
	private void createSelfLinkInCollections(String apiVersion, String apiKey, final AccountDTO accountDTO) 
			throws AccountNotFoundException {
		Link selfLink = linkTo(methodOn(AccountController.class).findById(apiVersion, apiKey, accountDTO.getId()))
				.withSelfRel().expand();
		accountDTO.add(selfLink);
	}
}
