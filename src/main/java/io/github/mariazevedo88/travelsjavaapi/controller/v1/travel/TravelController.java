package io.github.mariazevedo88.travelsjavaapi.controller.v1.travel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import io.github.mariazevedo88.travelsjavaapi.dto.model.travel.TravelDTO;
import io.github.mariazevedo88.travelsjavaapi.dto.response.Response;
import io.github.mariazevedo88.travelsjavaapi.exception.NotParsableContentException;
import io.github.mariazevedo88.travelsjavaapi.exception.TravelInvalidUpdateException;
import io.github.mariazevedo88.travelsjavaapi.exception.TravelNotFoundException;
import io.github.mariazevedo88.travelsjavaapi.model.travel.Travel;
import io.github.mariazevedo88.travelsjavaapi.service.travel.TravelService;
import io.github.mariazevedo88.travelsjavaapi.util.TravelsApiUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

/**
 * SpringBoot RestController that creates all service end-points related to the travel.
 * 
 * @author Mariana Azevedo
 * @since 08/09/2019
 */
@Log4j2
@RestController
@RequestMapping("/api-travels/v1/travels")
public class TravelController {
	
	TravelService travelService;
	
	@Autowired
	public TravelController(TravelService travelService) {
		this.travelService = travelService;
	}
	
	/**
	 * Method that creates travels in the database.
	 * 
	 * @author Mariana Azevedo
	 * @since 02/04/2020
	 * 
	 * @param apiVersion - API version at the moment
	 * @param apiKey - API Key to access the routes
	 * @param dto, where: 
	 * - id - trip id; 
	 * - orderNumber - identification number of a trip in the system; 
	 * - amount – travel amount; a string of arbitrary length that is parsable as a BigDecimal; 
	 * - startDate – initial date time in the ISO 8601 format YYYY-MM-DDThh:mm:ss.sssZ in the Local time zone;
	 * - endDate – final date time in the ISO 8601 format YYYY-MM-DDThh:mm:ss.sssZ in the Local time zone; 
	 * - type - trip type: RETURN (with a date to begin and end), ONE_WAY (only with initial date), 
	 * MULTI_CITY (with multiple destinations);
	 * - account_id - account id of the user in the API.
	 * 
	 * @param result - Bind result
	 * 
	 * @return ResponseEntity with a <code>Response<TravelDTO></code> object and the HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 201 - Created: Everything worked as expected.
	 * 400 - Bad Request: The request was unacceptable, often due to missing a required parameter.
	 * 404 - Not Found: The requested resource doesn't exist.
	 * 409 - Conflict: The request conflicts with another request (perhaps due to using the same idempotent key).
	 * 422 – Unprocessable Entity: if any of the fields are not parsable or the start date is greater than end date.
	 * 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential back-off of your requests.
	 * 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).
	 * 
	 * @throws NotParsableContentException
	 * @throws BadRequestException 
	 */
	@PostMapping
	@ApiOperation(value = "Route to create travels")
	public ResponseEntity<Response<TravelDTO>> create(@RequestHeader(value=TravelsApiUtil.HEADER_TRAVELS_API_VERSION, defaultValue="${api.version}") String apiVersion, 
			@RequestHeader(value=TravelsApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, @Valid @RequestBody TravelDTO dto, BindingResult result) 
					throws NotParsableContentException {
		
		Response<TravelDTO> response = new Response<>();

		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		if(!TravelsApiUtil.isTravelDTOEndDateGreaterThanStartDate(dto)) {
			throw new NotParsableContentException("The travel's start date is greater than travel's end date.");
		}
		
		Travel travel = dto.convertDTOToEntity(); 
		Travel travelToCreate = travelService.save(travel);

		TravelDTO dtoSaved = travelToCreate.convertEntityToDTO();
		createSelfLink(travelToCreate, dtoSaved);

		response.setData(dtoSaved);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(TravelsApiUtil.HEADER_TRAVELS_API_VERSION, apiVersion);
		headers.add(TravelsApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
	}
	
	/**
	 * Method that updates travels in the database.
	 * 
	 * @author Mariana Azevedo
	 * @since 02/04/2020
	 * 
	 * @param apiVersion - API version at the moment
	 * @param apiKey - API Key to access the routes
	 * @param dto, where: 
	 * - id - trip id; 
	 * - orderNumber - identification number of a trip in the system; 
	 * - amount – travel amount; a string of arbitrary length that is parsable as a BigDecimal; 
	 * - initialDate – initial date time in the ISO 8601 format YYYY-MM-DDThh:mm:ss.sssZ in the Local time zone;
	 * - finalDate – final date time in the ISO 8601 format YYYY-MM-DDThh:mm:ss.sssZ in the Local time zone; 
	 * - type - trip type: RETURN (with a date to begin and end), ONE_WAY (only with initial date), 
	 * MULTI_CITY (with multiple destinations);
	 * - account_id - account id of the user in the API.
	 * 
	 * @param result - Bind result
	 * 
	 * @return ResponseEntity with a <code>Response<TravelDTO></code> object and the HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Everything worked as expected.
	 * 400 - Bad Request: The request was unacceptable, often due to missing a required parameter.
	 * 404 - Not Found: The requested resource doesn't exist.
	 * 409 - Conflict: The request conflicts with another request (perhaps due to using the same idempotent key).
	 * 422 – Unprocessable Entity: if any of the fields are not parsable or the start date is greater than end date.
	 * 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential back-off of your requests.
	 * 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).
	 * 
	 * @throws TravelNotFoundException
	 * @throws TravelInvalidUpdateException
	 * @throws NotParsableContentException 
	 */
	@PutMapping(path = "/{id}")
	@ApiOperation(value = "Route to update a trip")
	public ResponseEntity<Response<TravelDTO>> update(@RequestHeader(value=TravelsApiUtil.HEADER_TRAVELS_API_VERSION, defaultValue="${api.version}") String apiVersion, 
		@RequestHeader(value=TravelsApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, @Valid @RequestBody TravelDTO dto, BindingResult result) 
		throws TravelNotFoundException, TravelInvalidUpdateException, NotParsableContentException {
		
		Response<TravelDTO> response = new Response<>();

		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		if(!TravelsApiUtil.isTravelDTOEndDateGreaterThanStartDate(dto)) {
			throw new NotParsableContentException("The travel's start date is greater than travel's end date.");
		}

		Travel travelToFind = travelService.findById(dto.getId());
		if (travelToFind.getId().compareTo(dto.getId()) != 0) {
			throw new TravelInvalidUpdateException("You don't have permission to change the travel id=" + dto.getId());
		}

		Travel travel = dto.convertDTOToEntity();
		Travel travelToUpdate = travelService.save(travel);
		
		TravelDTO itemDTO = travelToUpdate.convertEntityToDTO();
		createSelfLink(travelToUpdate, itemDTO);
		response.setData(itemDTO);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(TravelsApiUtil.HEADER_TRAVELS_API_VERSION, apiVersion);
		headers.add(TravelsApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}

	/**
	 * Method that search for all the travels saved in a period of time.
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
	 * @return ResponseEntity with a <code>Response<Page<TravelDTO>></code> object and the HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Everything worked as expected.
	 * 404 - Not Found: The requested resource doesn't exist.
	 * 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential back-off of your requests.
	 * 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).
	 * 
	 * @throws TravelNotFoundException 
	 */
	@GetMapping
	@ApiOperation(value = "Route to find all travels of the API in a period of time")
	public ResponseEntity<Response<List<TravelDTO>>> findAllBetweenDates(@RequestHeader(value=TravelsApiUtil.HEADER_TRAVELS_API_VERSION, defaultValue="${api.version}") 
		String apiVersion, @RequestHeader(value=TravelsApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") 
	    LocalDate startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate, 
	    @PageableDefault(page = 1, size = 10, sort = {"id"}) Pageable pageable) throws TravelNotFoundException {
		
		Response<List<TravelDTO>> response = new Response<>();
		
		LocalDateTime startDateTime = TravelsApiUtil.convertLocalDateToLocalDateTime(startDate);
		LocalDateTime endDateTime = TravelsApiUtil.convertLocalDateToLocalDateTime(endDate);
		
		Page<Travel> travels = travelService.findBetweenDates(startDateTime, endDateTime, pageable);
		
		if (travels.isEmpty()) {
			throw new TravelNotFoundException("There are no travels registered between startDate=" + startDate 
					+ " and endDate=" + endDate);
		}
		
		//Page<TravelDTO> itemsDTO = travels.map(t -> t.convertEntityToDTO());
		List<TravelDTO> itemsDTO = new ArrayList<>();
		travels.stream().forEach(t -> itemsDTO.add(t.convertEntityToDTO()));
		
		itemsDTO.stream().forEach(dto -> {
			try {
				createSelfLinkInCollections(apiVersion, apiKey, dto);
			} catch (TravelNotFoundException e) {
				log.error("There are no travels registered between startDate= {} and endDate= {}", startDate, endDate);
			}
		});
		
		response.setData(itemsDTO);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(TravelsApiUtil.HEADER_TRAVELS_API_VERSION, apiVersion);
		headers.add(TravelsApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}

	/**
	 * Method that search for all the travels given an order number.
	 * 
	 * @author Mariana Azevedo
	 * @since 02/04/2020
	 * 
	 * @param apiVersion - API version at the moment
	 * @param apiKey - API Key to access the routes
	 * @param travelOrderNumber - the order number of the travel
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
	@GetMapping(value = "/byOrderNumber/{orderNumber}")
	@ApiOperation(value = "Route to find a trip by the orderNumber in the API")
	public ResponseEntity<Response<List<TravelDTO>>> findByOrderNumber(@RequestHeader(value=TravelsApiUtil.HEADER_TRAVELS_API_VERSION, defaultValue="${api.version}") 
		String apiVersion, @RequestHeader(value=TravelsApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, 
		@PathVariable("orderNumber") String travelOrderNumber) throws TravelNotFoundException {
		
		Response<List<TravelDTO>> response = new Response<>();
		Optional<Travel> travels = travelService.findByOrderNumber(travelOrderNumber);
		
		if (travels.isEmpty()) {
			throw new TravelNotFoundException("There are no travels registered with the orderNumber=" + travelOrderNumber);
		}
		
		List<TravelDTO> travelsDTO = new ArrayList<>();
		travels.stream().forEach(t -> travelsDTO.add(t.convertEntityToDTO()));
		
		travelsDTO.stream().forEach(dto -> {
			try {
				createSelfLinkInCollections(apiVersion, apiKey, dto);
			} catch (TravelNotFoundException e) {
				log.error("There are no travels registered with the orderNumber= {}", travelOrderNumber);
			}
		});
		
		response.setData(travelsDTO);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(TravelsApiUtil.HEADER_TRAVELS_API_VERSION, apiVersion);
		headers.add(TravelsApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}
	
	/**
	 * Method that search a travel by the id.
	 * 
	 * @param apiVersion - API version at the moment
	 * @param apiKey - API Key to access the routes
	 * @param travelId - the id of the travel
	 * @param fields - Travel fields that should be returned in JSON as Partial Response
	 * 
	 * @return ResponseEntity with a <code>Response<TravelDTO></code> object and the HTTP status
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
	@ApiOperation(value = "Route to find a trip by your id in the API")
	public ResponseEntity<Response<TravelDTO>> findById(@RequestHeader(value=TravelsApiUtil.HEADER_TRAVELS_API_VERSION, defaultValue="${api.version}") 
		String apiVersion, @RequestHeader(value=TravelsApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, @PathVariable("id") Long travelId,
		@RequestParam(required = false) String fields) throws TravelNotFoundException {
		
		Response<TravelDTO> response = new Response<>();
		Travel travel = travelService.findById(travelId);
		
		TravelDTO dto = travel.convertEntityToDTO();
		
		if(fields != null) {
			dto = travelService.getPartialJsonResponse(fields, dto);
		}
		
		createSelfLink(travel, dto);
		response.setData(dto);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(TravelsApiUtil.HEADER_TRAVELS_API_VERSION, apiVersion);
		headers.add(TravelsApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}
	
	/**
	 * Method that delete a unique trip.
	 * 
	 * @author Mariana Azevedo
	 * @since 02/04/2020
	 * 
	 * @param apiVersion - API version at the moment
	 * @param apiKey - API Key to access the routes
	 * @param travelId - the id of the travel
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
	 * 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).
	 * 
	 * @throws TravelNotFoundException 
	 */
	@DeleteMapping(value = "/{id}")
	@ApiOperation(value = "Route to delete a trip in the API")
	public ResponseEntity<Response<String>> delete(@RequestHeader(value=TravelsApiUtil.HEADER_TRAVELS_API_VERSION, defaultValue="${api.version}") 
		String apiVersion, @RequestHeader(value=TravelsApiUtil.HEADER_API_KEY, defaultValue="${api.key}") String apiKey, 
		@PathVariable("id") Long travelId) throws TravelNotFoundException {
		
		Response<String> response = new Response<>();
		Travel travel = travelService.findById(travelId);
		
		travelService.deleteById(travel.getId());
		response.setData("Travel id=" + travel.getId() + " successfully deleted");
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(TravelsApiUtil.HEADER_TRAVELS_API_VERSION, apiVersion);
		headers.add(TravelsApiUtil.HEADER_API_KEY, apiKey);
		
		return new ResponseEntity<>(response, headers, HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Method that creates a self link to travel object
	 * 
	 * @author Mariana Azevedo
	 * @since 03/04/2020
	 * 
	 * @param travel
	 * @param travelDTO
	 */
	private void createSelfLink(Travel travel, TravelDTO travelDTO) {
		Link selfLink = WebMvcLinkBuilder.linkTo(TravelController.class).slash(travel.getId()).withSelfRel();
		travelDTO.add(selfLink);
	}
	
	/**
	 * Method that creates a self link in a collection of travels
	 * 
	 * @author Mariana Azevedo
	 * @since 03/04/2020
	 * 
	 * @param apiVersion - API version at the moment
	 * @param apiKey - API Key to access the routes
	 * @param travelDTO
	 * @throws TravelNotFoundException
	 */
	private void createSelfLinkInCollections(String apiVersion, String apiKey, final TravelDTO travelDTO) 
			throws TravelNotFoundException {
		Link selfLink = linkTo(methodOn(TravelController.class).findById(apiVersion, apiKey, travelDTO.getId(), null))
				.withSelfRel().expand();
		travelDTO.add(selfLink);
	}
	
}
