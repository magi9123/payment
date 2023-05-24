package org.example.testtools;

import org.example.dto.TransactionDto;
import org.example.model.Merchant;
import org.example.model.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionDtoTest {
    public static TransactionDto create(UUID uuid) {
        return TransactionDto.builder()
                .id(String.valueOf(uuid))
                .amount(BigDecimal.ONE)
                .status(TransactionStatus.APPROVED)
                .createAt(LocalDateTime.now())
                .referenceId(new Merchant())
                .build();
    }
}
