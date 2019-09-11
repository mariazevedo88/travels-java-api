package io.github.mariazevedo88.financialjavaapi.factory;

import io.github.mariazevedo88.financialjavaapi.model.Transaction;

public interface TransactionFactory {

	Transaction createTransaction(String type);
}
