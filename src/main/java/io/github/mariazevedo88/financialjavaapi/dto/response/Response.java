package io.github.mariazevedo88.financialjavaapi.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class that implements a generic response to the API endpoints.
 * 
 * @author Mariana Azevedo
 * @since 01/04/2020
 *
 * @param <T>
 */
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

	private T data;
	private Object errors;
	
	/**
	 * Method that formats an error message to the HTTP response.
	 * 
	 * @author Mariana Azevedo
	 * @since 01/04/2020
	 * 
	 * @param msgError
	 */
    public void addErrorMsgToResponse(String msgError) {
        ResponseError error = new ResponseError()
        		.setDetails(msgError)
                .setTimestamp(LocalDateTime.now());
        setErrors(error);
    }
}
