package io.github.mariazevedo88.financialjavaapi.factory;

import io.github.mariazevedo88.financialjavaapi.model.Transaction;

/**
 * Interface that provides method for manipulate a transaction.
 * 
 * @author Mariana Azevedo
 * @since 08/09/2019
 */
public interface TransactionFactory {

	Transaction createTransaction(String type);
}
