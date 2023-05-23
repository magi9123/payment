package org.example.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.xml.TransactionHandler;
import org.example.model.Transaction;
import org.example.repositories.TransactionRepository;
import org.example.services.TransactionService;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public void importFromXml(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        TransactionHandler handler = new TransactionHandler();
        parser.parse(inputStream,handler);
        System.out.println();
    }
}
