package io.github.mariazevedo88.financialjavaapi.util;

import java.time.LocalDateTime;

import io.github.mariazevedo88.financialjavaapi.dto.model.v1.transaction.TransactionDTO;
import io.github.mariazevedo88.financialjavaapi.model.v1.transaction.Transaction;

/**
 * Class that implements the FinancialAPI utility methods.
 * 
 * @author Mariana Azevedo
 * @since 28/03/2020
 */
public class FinancialApiUtil {
	
	/**
	 * Field to represent API version on the requests/responses header
	 */
	public static final String FINANCIAL_API_VERSION_HEADER = "goldgemapi-version";
	
	private FinancialApiUtil() {}
	
	/**
	 * Method that check if the Transaction is being created in the future.
	 * 
	 * @author Mariana Azevedo
	 * @since 08/09/2019
	 * 
	 * @param transaction
	 * @return boolean
	 */
	public static boolean isTransactionInFuture(Transaction transaction) {
		return transaction.getTransactionDate().isAfter(LocalDateTime.now());
	}
	
	/**
	 * Method that check if the TransactionDTO is being created in the future.
	 * 
	 * @author Mariana Azevedo
	 * @since 01/04/2020
	 * 
	 * @param dto
	 * @return boolean
	 */
	public static boolean isTransactionDTOInFuture(TransactionDTO dto) {
		return dto.getTransactionDate().isAfter(LocalDateTime.now());
	}
	
}
