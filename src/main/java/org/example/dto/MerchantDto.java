package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.MerchantStatus;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MerchantDto {
    private String name;
    private String description;
    private String email;
    private MerchantStatus status;
    private BigDecimal totalTransactionSum;
    private List<TransactionDto> transactionList;
}
