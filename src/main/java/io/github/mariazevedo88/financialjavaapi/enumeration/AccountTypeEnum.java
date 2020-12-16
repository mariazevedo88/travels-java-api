package io.github.mariazevedo88.financialjavaapi.enumeration;

public enum AccountTypeEnum {
	
	CHECKING_ACCOUNT("CHECKING_ACCOUNT"), 
	SAVINGS_ACCOUNT("SAVINGS_ACCOUNT");
	
	private String value;
	
	private AccountTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
