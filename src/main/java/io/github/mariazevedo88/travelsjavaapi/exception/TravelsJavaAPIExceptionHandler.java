package io.github.mariazevedo88.travelsjavaapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ServerErrorException;

import com.fasterxml.jackson.core.JsonParseException;

import io.github.mariazevedo88.travelsjavaapi.dto.response.Response;

/**
 * Class that implements a handler of exceptions and errors in the API, using {@ControllerAdvice} 
 * and sending the proper response to the client.
 * 
 * @author Mariana Azevedo
 * @since 01/04/2020
 *
 * @param <T>
 */
@ControllerAdvice
public class TravelsJavaAPIExceptionHandler<T> {
	
	/**
	 * Method that handles with a TravelInvalidUpdateException and returns an error with 
	 * status code = 403.
	 * 
	 * @author Mariana Azevedo
	 * @since 01/04/2020
	 * 
	 * @param exception
	 * @return ResponseEntity<Response<T>>
	 */
	@ExceptionHandler(value = { TravelInvalidUpdateException.class })
    protected ResponseEntity<Response<T>> handleTravelInvalidUpdateException(TravelInvalidUpdateException exception) {
		
		Response<T> response = new Response<>();
		response.addErrorMsgToResponse(exception.getLocalizedMessage());
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
	
	/**
	 * Method that handles with a TravelNotFoundException and returns an error with 
	 * status code = 404.
	 * 
	 * @author Mariana Azevedo
	 * @since 01/04/2020
	 * 
	 * @param exception
	 * @return ResponseEntity<Response<T>>
	 */
	@ExceptionHandler(value = { TravelNotFoundException.class })
    protected ResponseEntity<Response<T>> handleTravelNotFoundException(TravelNotFoundException exception) {
		
		Response<T> response = new Response<>();
		response.addErrorMsgToResponse(exception.getLocalizedMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
	
	/**
	 * Method that handles with a HttpClientErrorException and returns a Conflict
	 * error with status code = 409.
	 * 
	 * @author Mariana Azevedo
	 * @since 01/04/2020
	 * 
	 * @param exception
	 * @return ResponseEntity<Response<T>>
	 */
	@ExceptionHandler(value = { HttpClientErrorException.Conflict.class })
    protected ResponseEntity<Response<T>> handleConflictException(HttpClientErrorException exception) {
		
		Response<T> response = new Response<>();
		response.addErrorMsgToResponse(exception.getLocalizedMessage());
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
	
	/**
	 * Method that handles with a HttpMessageNotReadableException or JsonParseException and 
	 * returns an Unprocessable Entity error with status code = 422.
	 * 
	 * @author Mariana Azevedo
	 * @since 01/04/2020
	 * 
	 * @param exception
	 * @return ResponseEntity<Response<T>>
	 */
	@ExceptionHandler(value = { HttpMessageNotReadableException.class, JsonParseException.class, NotParsableContentException.class })
    protected ResponseEntity<Response<T>> handleMessageNotReadableException(Exception exception) {
		
		Response<T> response = new Response<>();
		response.addErrorMsgToResponse(exception.getLocalizedMessage());
		
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }
	
	/**
	 * Method that handles with a HttpClientErrorException and returns a TooManyRequests error 
	 * with status code = 429.
	 * 
	 * @author Mariana Azevedo
	 * @since 01/04/2020
	 * 
	 * @param exception
	 * @return ResponseEntity<Response<T>>
	 */
	@ExceptionHandler(value = { HttpClientErrorException.TooManyRequests.class })
    protected ResponseEntity<Response<T>> handleTooManyRequestException(HttpClientErrorException exception) {
		
		Response<T> response = new Response<>();
		response.addErrorMsgToResponse(exception.getLocalizedMessage());
		
		return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(response);
    }
	
	/**
	 * Method that handles with a TravelsJavaAPIException and returns an Internal Server Error 
	 * with status code = 500.
	 * 
	 * @author Mariana Azevedo
	 * @since 01/04/2020
	 * 
	 * @param exception
	 * @return ResponseEntity<Response<T>>
	 */
	@ExceptionHandler(value = { ServerErrorException.class })
    protected ResponseEntity<Response<T>> handleAPIException(ServerErrorException exception) {
		
		Response<T> response = new Response<>();
		response.addErrorMsgToResponse(exception.getLocalizedMessage());
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
