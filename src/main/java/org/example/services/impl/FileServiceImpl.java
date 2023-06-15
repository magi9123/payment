package org.example.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.infrastructure.exception.CodedException;
import org.example.services.FileService;
import org.example.xml.ValidationError;
import org.example.xml.facada.FileFacade;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileFacade fileFacade;

    @Override
    public ValidationError importFile(MultipartFile file) throws ParserConfigurationException, IOException, SAXException, CodedException {
        return fileFacade.saveFileToDatabase(file);

    }
}
