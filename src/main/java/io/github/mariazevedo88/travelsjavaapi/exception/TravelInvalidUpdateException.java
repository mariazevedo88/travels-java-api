package io.github.mariazevedo88.travelsjavaapi.exception;

/**
 * Class that implements TransactionInvalidUpdateException in the API.
 * 
 * @author Mariana Azevedo
 * @since 01/04/2020
 */
public class TravelInvalidUpdateException extends Exception{

	private static final long serialVersionUID = -6443362632495638948L;
	
	public TravelInvalidUpdateException(){
		super();
	}
	
	public TravelInvalidUpdateException(String msg){
		super(msg);
	}
	
	public TravelInvalidUpdateException(String msg, Throwable cause){
		super(msg, cause);
	}

}
