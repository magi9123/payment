package org.example.services;

import org.example.infrastructure.exception.CodedException;
import org.example.xml.ValidationError;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface FileService {
    ValidationError importFile(MultipartFile file) throws ParserConfigurationException, IOException, SAXException, CodedException;
}
