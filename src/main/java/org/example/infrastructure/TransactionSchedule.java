package org.example.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.repositories.TransactionRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class TransactionSchedule {

    private final TransactionRepository transactionRepository;

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void deleteTransaction() {
        var transactions = transactionRepository.findAllByCreatedAtBefore(LocalDateTime.now().minusHours(1));
        transactionRepository.deleteAll(transactions);

        log.info("Deleted transaction are " + transactions.size());
    }
}
