package org.example.file.parser;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MerchantParser {
    private String uuid;
    private String name;
    private String description;
    private String email;
    private int status;
    private BigDecimal bankAccountSum;
}
