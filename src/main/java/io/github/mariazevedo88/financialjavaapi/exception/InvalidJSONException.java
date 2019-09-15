package io.github.mariazevedo88.financialjavaapi.exception;

/**
 * Class that implements InvalidJSONException
 * 
 * @author Mariana Azevedo
 * @since 14/09/2019
 */
public class InvalidJSONException extends Exception {

	private static final long serialVersionUID = -4175972843472305288L;
	
	public InvalidJSONException(String msg){
		super(msg);
	}
	
	public InvalidJSONException(String msg, Throwable cause){
		super(msg, cause);
	}

}
