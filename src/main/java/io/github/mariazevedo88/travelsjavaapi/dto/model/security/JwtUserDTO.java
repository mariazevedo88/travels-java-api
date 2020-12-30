package io.github.mariazevedo88.travelsjavaapi.dto.model.security;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class that implements JWT Authentication data transfer object (DTO)
 * 
 * @author Mariana Azevedo
 * @since 11/10/2020
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtUserDTO {
	
	@NotNull(message = "Enter an email")
	@NotEmpty(message = "Enter an email")
	private String email;
	
	@NotNull(message = "Enter a password")
	@NotEmpty(message = "Enter a password")
	private String password;

}
