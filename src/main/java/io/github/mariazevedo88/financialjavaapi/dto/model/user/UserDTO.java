package io.github.mariazevedo88.financialjavaapi.dto.model.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.RepresentationModel;

import io.github.mariazevedo88.financialjavaapi.util.security.BcryptUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Class that implements User data transfer object (DTO)
 * 
 * @author Mariana Azevedo
 * @since 13/10/2020
 */
@Setter
@EqualsAndHashCode(callSuper = false)
public class UserDTO extends RepresentationModel<UserDTO> {
	
	@Getter
	private Long id;
	
	@Getter
	@NotNull(message = "Name cannot be null.")
	@Length(min=3, max=255, message="Name must contain between 3 and 255 characters.")
	private String name;
	
	@NotNull(message = "Password cannot be null.")
	@Length(min=6, message="Password must contain at least 6 characters.")
	private String password;
	
	@Getter
	@Length(max=100, message="Email must be a maximum of 100 characters.")
	@Email(message="Invalid email.")
	private String email;
	
	@Getter
	@NotNull(message="The User access role cannot be null.")
	@Pattern(regexp="^(ROLE_ADMIN|ROLE_USER)$", 
		message="For the access role only the values ROLE_ADMIN or ROLE_USER are accepted.")
	private String role;
	
	public UserDTO (Long id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
	}
	
	public String getPassword() {
		return BcryptUtil.getHash(this.password);
	}
}
