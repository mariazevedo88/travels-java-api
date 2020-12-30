package io.github.mariazevedo88.travelsjavaapi.controller.v1.user;

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

import io.github.mariazevedo88.travelsjavaapi.dto.model.user.UserDTO;
import io.github.mariazevedo88.travelsjavaapi.dto.response.Response;
import io.github.mariazevedo88.travelsjavaapi.model.user.User;
import io.github.mariazevedo88.travelsjavaapi.service.user.UserService;
import io.github.mariazevedo88.travelsjavaapi.util.TravelsApiUtil;

/**
 * SpringBoot RestController that implements all API service end-points related to the user.
 * 
 * @author Mariana Azevedo
 * @since 11/10/2020
 */
@RestController
@RequestMapping("/api-travels/v1/users")
public class UserController {
	
	@Autowired
	private UserService service;
	
	/**
	 * Method that creates an user in the API.
	 * 
	 * @param dto
	 * @param result
	 * @return ResponseEntity with a Response<UserDTO> object and the HTTP status
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
	public ResponseEntity<Response<UserDTO>> create(@RequestHeader(value=TravelsApiUtil.HEADER_TRAVELS_API_VERSION, defaultValue="${api.version}") 
		String apiVersion, @Valid @RequestBody UserDTO dto, BindingResult result){
		
		Response<UserDTO> response = new Response<>();
		
		if(result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		User user = service.save(dto.convertDTOToEntity());
		UserDTO userDTO = user.convertEntityToDTO();
		
		//Self link
		createSelfLink(user, userDTO);
		response.setData(userDTO);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(TravelsApiUtil.HEADER_TRAVELS_API_VERSION, apiVersion);
		
		return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
	}

	/**
	 * Method that creates a self link to User object
	 * 
	 * @author Mariana Azevedo
	 * @since 11/10/2020
	 * 
	 * @param user
	 * @param userDTO
	 */
	private void createSelfLink(User user, UserDTO userDTO) {
		Link selfLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(user.getId()).withSelfRel();
		userDTO.add(selfLink);
	}

}
