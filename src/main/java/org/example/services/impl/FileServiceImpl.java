package org.example.services.impl;

import org.example.services.FileService;
import org.example.xml.facada.TransactionXmlRead;
import org.example.xml.facada.TransactionsXmlValidation;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public void importFromXml(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        TransactionXmlRead read = new TransactionXmlRead();
        var transactionsXml = read.readXml(inputStream);

        TransactionsXmlValidation validation = new TransactionsXmlValidation();
        validation.validateXml(transactionsXml);


    }
}
