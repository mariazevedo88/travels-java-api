package io.github.mariazevedo88.financialjavaapi.dto.response;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Class that implements a generic response error object to the API endpoints.
 * 
 * @author Mariana Azevedo
 * @since 01/04/2020
 */
@Accessors(chain = true)
@NoArgsConstructor
public class ResponseError {
	
	@Getter
	@Setter
	@NotNull(message="Timestamp cannot be null")
	private LocalDateTime timestamp;
	
	@Getter
	@Setter
	@NotNull(message="Details cannot be null")
    private String details;

}