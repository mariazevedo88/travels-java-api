package io.github.mariazevedo88.financialjavaapi.factory.impl;

import io.github.mariazevedo88.financialjavaapi.factory.TransactionFactory;
import io.github.mariazevedo88.financialjavaapi.model.Transaction;

public class TransactionFactoryImpl implements TransactionFactory{

	@Override
	public Transaction createTransaction() {
		return new Transaction();
	}

}
