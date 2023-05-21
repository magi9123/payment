package org.example.services;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@Sql("/merchant.sql")
class MerchantServiceTest {

    public static final int MERCHANT_SIZE = 3;
    public static final String NAME = "Maggie";

    @Autowired
    private MerchantService merchantService;

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
}