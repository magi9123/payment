package org.example.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.repositories.TransactionRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class TransactionSchedule {

    private final TransactionRepository transactionRepository;

    @Scheduled(fixedDelay = 60 * 60 * 100)
    public void deleteTransaction() {
        var transactions = transactionRepository.findAll();

        var transactionDel = transactions.stream()
                .filter(transaction -> transaction.getCreatedAt().isBefore(LocalDateTime.now().minusHours(1)))
                .collect(Collectors.toList());

        transactionRepository.deleteAll(transactionDel);

        log.info("Deleted transaction are " + transactionDel.size());
    }
}
