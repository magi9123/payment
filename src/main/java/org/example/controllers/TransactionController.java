package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.ResponseDto;
import org.example.dto.TransactionDto;
import org.example.infrastructure.exception.CodedException;
import org.example.mapper.TransactionMapper;
import org.example.services.FileService;
import org.example.services.TransactionService;
import org.example.xml.ValidationError;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping(TransactionController.BASE)
@RequiredArgsConstructor
public class TransactionController {

    public static final String BASE = "/api/payment_system/transaction";

    private final TransactionService transactionService;
    private final FileService fileService;
    private final TransactionMapper mapper;

    @GetMapping
    public ResponseDto<List<TransactionDto>> getAllTransactions() {
        return ResponseDto.response(mapper.toDtos(transactionService.findAllTransactions()));
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDto<ValidationError> uploadTransactions(@RequestPart MultipartFile file) throws IOException, ParserConfigurationException, SAXException, CodedException {
        return ResponseDto.response(fileService.importFile(file));
    }

    @DeleteMapping("/{id}")
    public ResponseDto<TransactionDto> deleteMerchant(@PathVariable("id") String id) throws CodedException {
        return ResponseDto.response(mapper.toDto(transactionService.deleteTransaction(UUID.fromString(id))));
    }
}
