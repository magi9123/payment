package org.example.services;

import org.example.infrastructure.exception.CodedException;
import org.example.model.Transaction;
import java.util.List;
import java.util.UUID;

public interface TransactionService {
    List<Transaction> findAllTransactions();

    Transaction deleteTransaction(UUID uuid) throws CodedException;
}
