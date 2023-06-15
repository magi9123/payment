package org.example.xml.parser;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class TransactionParser {
    private String uuid;
    private BigDecimal amount;
    private int type;
    private CustomerParser customer;
    private MerchantParser merchant;
}
