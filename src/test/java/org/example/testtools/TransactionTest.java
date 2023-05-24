package org.example.testtools;

import org.example.model.Merchant;
import org.example.model.Transaction;
import org.example.model.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionTest {
    public static Transaction create(UUID uuid) {
        return new Transaction(uuid,BigDecimal.ONE,TransactionStatus.APPROVED,"some@gmail.com",""
                ,LocalDateTime.now(),new Merchant());
    }
}
