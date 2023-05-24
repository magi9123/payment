package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.ResponseDto;
import org.example.dto.TransactionDto;
import org.example.mapper.TransactionMapper;
import org.example.services.FileService;
import org.example.services.TransactionService;
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

    private final TransactionService transactionService;
    private final FileService fileService;
    private final TransactionMapper mapper;

    @GetMapping
    public ResponseDto<List<TransactionDto>> getAllTransactions() {
        return ResponseDto.response(mapper.toDtos(transactionService.findAllTransactions()));
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDto<Void> uploadTransactions(@RequestPart MultipartFile file) throws IOException, ParserConfigurationException, SAXException {
        fileService.importFromXml(file.getInputStream());
        return ResponseDto.response();
    }
}
