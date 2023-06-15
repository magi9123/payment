package org.example.file.parser;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CustomerParser {
    private String email;
    private String phone;
    private BigDecimal bankAccountSum;
}
