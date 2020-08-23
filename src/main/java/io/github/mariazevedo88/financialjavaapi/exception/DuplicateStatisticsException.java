package io.github.mariazevedo88.financialjavaapi.exception;

/**
 * Class that implements DuplicateStatisticsException in the API
 * 
 * @author Mariana Azevedo
 * @since 23/08/2020
 */
public class DuplicateStatisticsException extends Exception {

	private static final long serialVersionUID = 6082551323004629906L;
	
	public DuplicateStatisticsException(){
		super();
	}
	
	public DuplicateStatisticsException(String msg){
		super(msg);
	}
	
	public DuplicateStatisticsException(String msg, Throwable cause){
		super(msg, cause);
	}

}
