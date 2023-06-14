package org.example.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.infrastructure.exception.CodedException;
import org.example.infrastructure.exception.ErrorCode;
import org.example.model.Transaction;
import org.example.repositories.TransactionRepository;
import org.example.services.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction deleteTransaction(UUID uuid) throws CodedException {
        var transaction = transactionRepository.findById(uuid).orElseThrow(() -> new CodedException(ErrorCode.TRANSACTION_NOT_FOUND, uuid));
        transactionRepository.delete(transaction);

        return transaction;
    }
}
