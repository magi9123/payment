package org.example.xml.facada;

import lombok.RequiredArgsConstructor;
import org.example.model.Merchant;
import org.example.model.MerchantStatus;
import org.example.model.Transaction;
import org.example.model.TransactionStatus;
import org.example.repositories.MerchantRepository;
import org.example.xml.parser.MerchantParser;
import org.example.xml.parser.TransactionParser;
import org.example.xml.parser.TransactionsParser;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.example.model.TransactionStatus.*;

@Component
@RequiredArgsConstructor
public class TransactionsFileSave {
    private final MerchantRepository merchantRepository;

    public void saveData(TransactionsParser transactionsParser) {

        HashMap<UUID, List<TransactionParser>> merchantTransactionsList = unionTransactionsByMerchant(transactionsParser);

        for (var merchantTransactions : merchantTransactionsList.entrySet()) {

            var merchantXml = merchantTransactions.getValue().get(0).getMerchant();
            if (isMerchantActive(merchantXml.getStatus())) {

                Merchant merchant = createMerchantEntity(merchantXml);

                BigDecimal merchantBankAccountStatement = merchantXml.getBankAccountSum();

                var transactionList = new ArrayList<Transaction>();
                for (var transaction : merchantTransactions.getValue()) {

                    TransactionStatus transactionStatus = TransactionStatus.checkIfExist(transaction.getType());
                    switch (transactionStatus) {
                        case APPROVED:
                            merchantBankAccountStatement = calculateAccountsBalanceForApproved(merchantBankAccountStatement, transaction);
                            addTransactionToMerchant(transaction, TransactionStatus.APPROVED, merchant, transactionList);
                            break;
                        case REFUNDED:
                            merchantBankAccountStatement = calculateAccountsBalanceForRefunded(merchantBankAccountStatement, transaction);
                            addTransactionToMerchant(transaction, REFUNDED, merchant, transactionList);
                            break;
                        case REVERSED:
                            addTransactionToMerchant(transaction, TransactionStatus.REVERSED, merchant, transactionList);
                            break;
                        case ERROR:
                            addTransactionToMerchant(transaction, TransactionStatus.ERROR, merchant, transactionList);
                            break;
                        default:
                            break;
                    }
                }

                merchant.setTotalTransactionSum(merchantBankAccountStatement);
                merchant.setTransactionList(transactionList);
                merchantRepository.save(merchant);

            }
        }
    }

    private Merchant createMerchantEntity(MerchantParser merchantParser) {
        return new Merchant(UUID.fromString(merchantParser.getUuid()), merchantParser.getName(), merchantParser.getDescription(),
                merchantParser.getEmail(), getStatus(merchantParser.getStatus()), merchantParser.getBankAccountSum(), null);
    }

    private static HashMap<UUID, List<TransactionParser>> unionTransactionsByMerchant(TransactionsParser transactionsParser) {
        var merchantTransactions = new HashMap<UUID, List<TransactionParser>>();

        for (var transactionXml : transactionsParser.getArticleList()) {
            var uuid = UUID.fromString(transactionXml.getMerchant().getUuid());
            merchantTransactions.putIfAbsent(uuid, new ArrayList<>());
            merchantTransactions.get(uuid).add(transactionXml);
        }
        return merchantTransactions;
    }

    private boolean isMerchantActive(int status) {
        return MerchantStatus.ACTIV.ordinal() == status;
    }

    private MerchantStatus getStatus(int merchantStatus) {
        return MerchantStatus.ACTIV.ordinal() == merchantStatus ? MerchantStatus.ACTIV : MerchantStatus.INACTIVE;
    }

    private BigDecimal calculateAccountsBalanceForRefunded(BigDecimal accountBalance, TransactionParser transaction) {
        accountBalance = accountBalance.subtract(transaction.getAmount());
        transaction.getCustomer().getBankAccountSum().add(transaction.getAmount());//fake
        return accountBalance;
    }

    private BigDecimal calculateAccountsBalanceForApproved(BigDecimal accountBalance, TransactionParser transaction) {
        accountBalance = accountBalance.add(transaction.getAmount());
        transaction.getCustomer().getBankAccountSum().subtract(transaction.getAmount());//fake
        return accountBalance;
    }

    private static void addTransactionToMerchant(TransactionParser transaction, TransactionStatus status, Merchant merchant, ArrayList<Transaction> transactionList) {
        Transaction transactionEntity = new Transaction(UUID.fromString(transaction.getUuid()), transaction.getAmount(),
                status, transaction.getCustomer().getEmail(),
                transaction.getCustomer().getPhone(), LocalDateTime.now(), merchant);

        transactionList.add(transactionEntity);
    }
}
