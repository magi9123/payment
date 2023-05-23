package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.ResponseDto;
import org.example.dto.TransactionDto;
import org.example.mapper.TransactionMapper;
import org.example.services.impl.TransactionServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
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


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDto<List<TransactionDto>> uploadTransactions(@RequestPart MultipartFile file) throws IOException, ParserConfigurationException, SAXException {
        transactionService.importFromXml(file.getInputStream());
        return ResponseDto.response(List.of(new TransactionDto()));
    }
}
