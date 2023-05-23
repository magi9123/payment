package org.example.xml;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MerchantXml {
    private String name;
    private String description;
    private String email;
    private int status;
    private BigDecimal bankAccountSum;
}