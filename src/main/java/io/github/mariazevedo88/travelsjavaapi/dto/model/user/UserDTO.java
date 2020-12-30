package io.github.mariazevedo88.travelsjavaapi.dto.model.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import io.github.mariazevedo88.travelsjavaapi.model.user.User;
import io.github.mariazevedo88.travelsjavaapi.util.security.BcryptUtil;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class that implements User data transfer object (DTO)
 * 
 * @author Mariana Azevedo
 * @since 13/10/2020
 */
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
	
	public UserDTO (Long id, String email, String password) {
		this.id = id;
		this.email = email;
		this.password = password;
	}
	
	public String getPassword() {
		return BcryptUtil.getHash(this.password);
	}
	
	/**
	 * Method to convert an User DTO to an User entity
	 * 
	 * @author Mariana Azevedo
	 * @since 11/10/2020
	 * 
	 * @param dto
	 * @return an User object
	 */
	public User convertDTOToEntity() {
		return new ModelMapper().map(this, User.class);
	}
}
