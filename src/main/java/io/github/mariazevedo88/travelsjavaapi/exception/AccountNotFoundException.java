package io.github.mariazevedo88.travelsjavaapi.exception;

/**
 * Class that implements AccountNotFoundException in the API
 * 
 * @author Mariana Azevedo
 * @since 31/10/2020
 */
public class AccountNotFoundException extends Exception {

	private static final long serialVersionUID = -7139304880555402679L;
	
	public AccountNotFoundException(){
		super();
	}
	
	public AccountNotFoundException(String msg){
		super(msg);
	}
	
	public AccountNotFoundException(String msg, Throwable cause){
		super(msg, cause);
	}

}
