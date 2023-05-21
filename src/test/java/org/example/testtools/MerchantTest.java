package org.example.testtools;

import org.example.model.Merchant;
import org.example.model.MerchantStatus;
import org.example.model.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MerchantTest {
    public static Merchant create(UUID uuid) {

        return new Merchant(uuid, "Katq", "desc", "test@email.com", MerchantStatus.ACTIV,
                BigDecimal.ONE, List.of(new Transaction()));
    }
}
