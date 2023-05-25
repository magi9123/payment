package org.example.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.services.FileService;
import org.example.xml.ValidationError;
import org.example.xml.facada.FileFacada;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileFacada fileFacada;

    @Override
    public ValidationError importFromXml(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        return fileFacada.saveFileToDatabase(inputStream);
    }
}
