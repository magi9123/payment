package org.example.xml.facada;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.infrastructure.exception.CodedException;
import org.example.infrastructure.exception.ErrorCode;
import org.example.xml.ValidationError;
import org.example.xml.csv.TransactionCsv;
import org.example.xml.parser.TransactionParser;
import org.example.xml.parser.TransactionsParser;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FileFacade {
    public static final String XML_TYPE = "application/xml";
    public static final String CSV_TYPE = "text/csv";

    private final TransactionsXmlRead read = new TransactionsXmlRead();
    private final TransactionCsvRead readCsv = new TransactionCsvRead();
    private final ObjectMapper objectMapper;
    private final TransactionsFileValidation transactionsFileValidation = new TransactionsFileValidation();
    private final TransactionsFileSave transactionsFileSave;

    public ValidationError saveFileToDatabase(MultipartFile file) throws ParserConfigurationException, IOException, SAXException, CodedException {
        if (file.isEmpty()) {
            throw new CodedException(ErrorCode.FILE_IS_EMPTY,file.getOriginalFilename());
        }

        var inputStream = file.getInputStream();
        if (file.getContentType().equals(XML_TYPE)) {
            saveFromXml(inputStream);
        } else if (file.getContentType().equals(CSV_TYPE)) {
            saveFromCsv(inputStream);
        } else {
            throw new CodedException(ErrorCode.FILE_FORMAT_NOT_SUPPORTED,file.getContentType());
        }

        return ValidationError.getValidationError();
    }

    private void saveFromCsv(InputStream inputStream) {
        var transactionCsvList = readCsv.readCsv(inputStream);

        var transactionsParser = createParserObjectFromCsvObject(transactionCsvList);

        transactionsFileValidation.validateData(transactionsParser);
        transactionsFileSave.saveData(transactionsParser);
    }

    private void saveFromXml(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {
        var transactionsXml = read.readXml(inputStream);
        transactionsFileValidation.validateData(transactionsXml);
        transactionsFileSave.saveData(transactionsXml);
    }

    private TransactionsParser createParserObjectFromCsvObject(List<TransactionCsv> transactionCsvList) {
        var transactionsParser = new TransactionsParser();

        var transactionParserList = transactionCsvList.stream()
                .map(t -> objectMapper.convertValue(t, TransactionParser.class))
                .collect(Collectors.toList());

        transactionsParser.setArticleList(transactionParserList);
        return transactionsParser;
    }
}
