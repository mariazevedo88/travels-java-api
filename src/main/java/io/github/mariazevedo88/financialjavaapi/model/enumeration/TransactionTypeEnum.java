package io.github.mariazevedo88.financialjavaapi.model.enumeration;

/**
 * Enum that classifies the transaction's payment type.
 * 
 * @author Mariana Azevedo
 * @since 14/09/2019
 */
public enum TransactionTypeEnum {
	
	CARD("CARD"), MONEY("MONEY");
	
	private String value;
	
	private TransactionTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
