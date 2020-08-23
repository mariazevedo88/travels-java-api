package io.github.mariazevedo88.financialjavaapi.model.enumeration;

public enum PageOrderEnum {
	
	ASC("ASC"), DESC("DESC");

	private String value;
	
	private PageOrderEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public static PageOrderEnum getDirection(String value){
		if(ASC.getValue().equals(value)) {
			return ASC;
		}
		return DESC;
	}
}
