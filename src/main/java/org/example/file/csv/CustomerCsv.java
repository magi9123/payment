package org.example.file.csv;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CustomerCsv {
    @CsvBindByName(column = "customer/customer_email")
    private String email;

    @CsvBindByName(column = "customer/phone")
    private String phone;

    @CsvBindByName(column = "customer/bankAccountSumCustomer")
    private BigDecimal bankAccountSum;
}
