package io.github.mariazevedo88.travelsjavaapi.exception;

/**
 * Class that implements TransactionNotFoundException in the API
 * 
 * @author Mariana Azevedo
 * @since 14/09/2019
 */
public class TravelNotFoundException extends Exception {

	private static final long serialVersionUID = -2586209354700102349L;
	
	public TravelNotFoundException(){
		super();
	}
	
	public TravelNotFoundException(String msg){
		super(msg);
	}
	
	public TravelNotFoundException(String msg, Throwable cause){
		super(msg, cause);
	}
	
}
