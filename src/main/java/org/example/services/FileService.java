package org.example.services;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {
    void importFromXml(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException;

}
