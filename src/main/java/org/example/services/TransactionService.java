package org.example.services;

import org.example.model.Transaction;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface TransactionService {
    List<Transaction> findAllTransactions();
}
