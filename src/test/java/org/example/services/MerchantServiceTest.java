package org.example.services;

import lombok.SneakyThrows;
import org.example.infrastructure.exception.CodedException;
import org.example.infrastructure.exception.ErrorCode;
import org.example.model.Transaction;
import org.example.model.TransactionStatus;
import org.example.repositories.TransactionRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@Sql("/merchant.sql")
class MerchantServiceTest {

    public static final int MERCHANT_SIZE = 3;
    public static final String NAME = "Maggie";

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void shouldFindAllMerchants() {
        var merchantList = merchantService.findAllMerchants();

        assertThat(merchantList.size()).isEqualTo(MERCHANT_SIZE);
        assertThat(merchantList.get(0).getName()).isEqualTo(NAME);
    }

    @Test
    @SneakyThrows
    void shouldDeleteMerchantWhenMerchantDoesNotHaveRelateTransaction() {
        var merchantBeforeDelete = merchantService.findAllMerchants();
        merchantService.deleteMerchant(merchantBeforeDelete.get(0).getId());

        var merchantAfterDelete = merchantService.findAllMerchants().size();

        assertEquals(merchantAfterDelete, merchantBeforeDelete.size() - 1);
    }

    @Test
    @SneakyThrows
    void shouldThrowExceptionWhenMerchantHaveRelateTransaction() {
        var merchant = merchantService.findAllMerchants().get(0);

        var transaction = new Transaction(UUID.randomUUID(), BigDecimal.valueOf(321312.00), TransactionStatus.APPROVED,
                "someemail@gmail.com", "08776567567",
                merchant);
        transactionRepository.saveAndFlush(transaction);

        var exception = assertThrows(CodedException.class, () -> {
            merchantService.deleteMerchant(merchant.getId());
        });

        assertEquals(ErrorCode.MERSHANT_TRANSACTION.getCode(), exception.getCode());
    }
}