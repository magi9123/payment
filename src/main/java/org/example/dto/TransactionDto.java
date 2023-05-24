package org.example.dto;

import lombok.*;
import org.example.model.Merchant;
import org.example.model.TransactionStatus;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private String id;
    private BigDecimal amount;
    private TransactionStatus status;
    private String customerEmail;
    private String customerPhone;
    private LocalDateTime createAt;
    private Merchant referenceId;
}
