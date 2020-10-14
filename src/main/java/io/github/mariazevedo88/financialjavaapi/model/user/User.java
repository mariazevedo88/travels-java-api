package io.github.mariazevedo88.financialjavaapi.model.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.github.mariazevedo88.financialjavaapi.model.enumeration.RoleEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * Class that implements an User entity in the API.
 * 
 * @author Mariana Azevedo
 * @since 11/10/2020
 */
@Entity
@Getter
@Setter
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = 5514528747731992863L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String email;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private RoleEnum role;
	
	/**
	 * Method that verifies if the user is admin
	 * 
	 * @author Mariana Azevedo
	 * @since 11/10/2020
	 * 
	 * @return boolean
	 */
	public boolean isAdmin() {
		return RoleEnum.ROLE_ADMIN.toString().equals(this.role.toString());
	}

}
