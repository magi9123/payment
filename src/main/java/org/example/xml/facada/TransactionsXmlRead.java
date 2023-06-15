package org.example.xml.facada;

import org.example.xml.TransactionHandler;
import org.example.xml.parser.TransactionsParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;

public class TransactionsXmlRead {
    public TransactionsParser readXml(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        TransactionHandler handler = new TransactionHandler();
        parser.parse(inputStream, handler);

        return handler.getTransactionsParser();
    }
}
