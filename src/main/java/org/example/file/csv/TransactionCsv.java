package org.example.file.csv;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvRecurse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class TransactionCsv {
    @CsvBindByName(column = "_id")
    private String uuid;

    @CsvBindByName
    private BigDecimal amount;

    @CsvBindByName
    private int type;

    @CsvRecurse
    private CustomerCsv customer;

    @CsvRecurse
    private MerchantCsv merchant;
}
