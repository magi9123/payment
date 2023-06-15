package org.example.file.facada;

import com.opencsv.bean.CsvToBeanBuilder;
import org.example.file.csv.TransactionCsv;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class TransactionCsvRead {
    public List<TransactionCsv> readCsv(InputStream inputStream) {
        var reader = new BufferedReader(new InputStreamReader(inputStream));
        return new CsvToBeanBuilder<TransactionCsv>(reader)
                .withType(TransactionCsv.class)
                .build()
                .parse();
    }
}
