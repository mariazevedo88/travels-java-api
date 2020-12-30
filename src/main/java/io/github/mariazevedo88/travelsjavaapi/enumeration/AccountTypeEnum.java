package io.github.mariazevedo88.travelsjavaapi.enumeration;

/**
 * Enum that classifies the account's type in the API.
 * 
 * @author Mariana Azevedo
 * @since 16/12/2020
 * 
 * @author Mariana Azevedo
 *
 */
public enum AccountTypeEnum {
	
	FREE("FREE"),
	BASIC("BASIC"), 
	PREMIUM("PREMIUM");
	
	private String value;
	
	private AccountTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
