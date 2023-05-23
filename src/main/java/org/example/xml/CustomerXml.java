package org.example.xml;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CustomerXml {
    private String email;
    private String phone;
    private BigDecimal bankAccountSum;
}
