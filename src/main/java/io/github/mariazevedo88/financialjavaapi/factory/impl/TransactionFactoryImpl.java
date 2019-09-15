package io.github.mariazevedo88.financialjavaapi.factory.impl;

import io.github.mariazevedo88.financialjavaapi.enumeration.TransactionTypeEnum;
import io.github.mariazevedo88.financialjavaapi.factory.TransactionFactory;
import io.github.mariazevedo88.financialjavaapi.model.Transaction;

public class TransactionFactoryImpl implements TransactionFactory{

	@Override
	public Transaction createTransaction(String type) {
		if(TransactionTypeEnum.MONEY.getValue().equals(type)) {
			return new Transaction(TransactionTypeEnum.MONEY);
		}
		return new Transaction(TransactionTypeEnum.CARD);
	}

}
