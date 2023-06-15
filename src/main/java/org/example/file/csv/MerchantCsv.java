package org.example.file.csv;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MerchantCsv {
    @CsvBindByName(column = "merchant/_id")
    private String uuid;

    @CsvBindByName(column = "merchant/name")
    private String name;

    @CsvBindByName(column = "merchant/description")
    private String description;

    @CsvBindByName(column = "merchant/merchant_email")
    private String email;

    @CsvBindByName(column = "merchant/status")
    private int status;

    @CsvBindByName(column = "merchant/bankAccountSumMerchant")
    private BigDecimal bankAccountSum;
}
