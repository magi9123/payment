package org.example.services;

import org.example.model.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> findAllTransactions();
}
