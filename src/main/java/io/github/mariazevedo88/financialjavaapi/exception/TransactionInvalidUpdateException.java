package io.github.mariazevedo88.financialjavaapi.exception;

/**
 * Class that implements TransactionInvalidUpdateException in the API.
 * 
 * @author Mariana Azevedo
 * @since 01/04/2020
 */
public class TransactionInvalidUpdateException extends Exception{

	private static final long serialVersionUID = -6443362632495638948L;
	
	public TransactionInvalidUpdateException(){
		super();
	}
	
	public TransactionInvalidUpdateException(String msg){
		super(msg);
	}
	
	public TransactionInvalidUpdateException(String msg, Throwable cause){
		super(msg, cause);
	}

}
