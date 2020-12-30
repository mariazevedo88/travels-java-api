package io.github.mariazevedo88.travelsjavaapi.dto.model.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Class that implements Token data transfer object (DTO)
 * 
 * @author Mariana Azevedo
 * @since 11/10/2020
 */
@AllArgsConstructor
public class TokenDTO {
	
	@Getter
	private String token;

}
