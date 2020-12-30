package io.github.mariazevedo88.travelsjavaapi.enumeration;

/**
 * Enum that represents the two types of roles in the API: ROLE_ADMIN and ROLE_USER.
 * 
 * @author Mariana Azevedo
 * @since 11/10/2020
 */
public enum RoleEnum {
	
	ROLE_ADMIN("ROLE_ADMIN"), 
	ROLE_USER("ROLE_USER");
	
	private String value;
	
	private RoleEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
