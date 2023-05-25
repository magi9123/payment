package org.example.xml.facada;

import lombok.RequiredArgsConstructor;
import org.example.xml.ValidationError;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class FileFacada {
    private final TransactionXmlRead read = new TransactionXmlRead();
    private final TransactionsXmlValidation validation = new TransactionsXmlValidation();
    private final TransactionXmlSave save;

    public ValidationError saveFileToDatabase(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        var transactionsXml = read.readXml(inputStream);
        validation.validateXml(transactionsXml);
        save.saveXml(transactionsXml);

        return ValidationError.getValidationError();
    }
}
