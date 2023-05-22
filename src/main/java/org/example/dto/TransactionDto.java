package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.Merchant;
import org.example.model.TransactionStatus;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private String uuid;
    private BigDecimal amount;
    private TransactionStatus status;
    private String customerEmail;
    private String customerPhone;
    private LocalDateTime createAt;
    private Merchant referenceId;
}
