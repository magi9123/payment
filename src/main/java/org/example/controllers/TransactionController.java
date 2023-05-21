package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.ResponseDto;
import org.example.dto.TransactionDto;
import org.example.mapper.TransactionMapper;
import org.example.services.impl.TransactionServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(TransactionController.BASE)
@RequiredArgsConstructor
public class TransactionController {

    public static final String BASE = "/api/payment_system/transaction";

    private final TransactionServiceImpl transactionService;
    private final TransactionMapper mapper;

    @GetMapping
    public ResponseDto<List<TransactionDto>> getAllTransactions() {
        return ResponseDto.response(mapper.toDtos(transactionService.findAllTransactions()));
    }

}
