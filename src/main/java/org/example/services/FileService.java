package org.example.services;

import org.example.xml.ValidationError;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {
    ValidationError importFromXml(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException;
}
