package com.Raj.service;

import com.Raj.model.Orders;
import com.Raj.model.Seller;
import com.Raj.model.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction createTransaction(Orders order);

    List<Transaction> getTransactionsBySellerId(Seller seller);

    List<Transaction> getAllTransactions();

}
