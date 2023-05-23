package org.example.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.model.Transaction;
import org.example.repositories.TransactionRepository;
import org.example.services.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }
}
