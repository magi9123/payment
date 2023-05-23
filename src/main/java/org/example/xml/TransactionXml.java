package org.example.xml;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class TransactionXml {
    private String uuid;
    private BigDecimal amount;
    private int type;
    private CustomerXml customer;
    private MerchantXml merchant;
}
