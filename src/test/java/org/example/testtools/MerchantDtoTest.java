package org.example.testtools;

import org.example.dto.MerchantDto;
import org.example.dto.TransactionDto;
import org.example.model.MerchantStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MerchantDtoTest {
    public static MerchantDto create(UUID uuid) {
        return new MerchantDto(uuid, "Katq", "desc", "test@email.com", MerchantStatus.ACTIV,
                BigDecimal.ONE, List.of(new TransactionDto()));
    }
}
