package org.example.xml.facada;

import lombok.RequiredArgsConstructor;
import org.example.model.Merchant;
import org.example.model.MerchantStatus;
import org.example.model.Transaction;
import org.example.model.TransactionStatus;
import org.example.repositories.MerchantRepository;
import org.example.xml.TransactionXml;
import org.example.xml.TransactionsXml;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.example.model.TransactionStatus.*;

@Component
@RequiredArgsConstructor
public class TransactionXmlSave {
    private final MerchantRepository merchantRepository;

    public void saveXml(TransactionsXml transactionsXml) {

        HashMap<UUID, List<TransactionXml>> merchantTransactions = unionTransactionsByMerchant(transactionsXml);

        for (var merchant : merchantTransactions.entrySet()) {

            var merchantStatus = merchant.getValue().get(0).getMerchant().getStatus();
            if (isMerchantActive(merchantStatus)) {

                Merchant merchantEntity = createMerchantEntity(merchant);

                BigDecimal merchantBankAccountStatement = merchant.getValue().get(0).getMerchant().getBankAccountSum();

                var transactionList = new ArrayList<Transaction>();
                for (var transaction : merchant.getValue()) {

                    var transactionStatus = TransactionStatus.checkIfExist(transaction.getType()).name();
                    switch (transactionStatus) {
                        case "APPROVED":
                            merchantBankAccountStatement = calculateAccountsBalanceForApproved(merchantBankAccountStatement, transaction);
                            addTransactionToMerchant(transaction, TransactionStatus.APPROVED, merchantEntity, transactionList);
                            break;
                        case "REFUNDED":
                            merchantBankAccountStatement = calculateAccountsBalanceForRefunded(merchantBankAccountStatement, transaction);
                            addTransactionToMerchant(transaction, REFUNDED, merchantEntity, transactionList);
                            break;
                        case "REVERSED":
                            addTransactionToMerchant(transaction, TransactionStatus.REVERSED, merchantEntity, transactionList);
                            break;
                        case "ERROR":
                            addTransactionToMerchant(transaction, TransactionStatus.ERROR, merchantEntity, transactionList);
                            break;
                        default:
                            break;
                    }
                }

                merchantEntity.setTotalTransactionSum(merchantBankAccountStatement);
                merchantEntity.setTransactionList(transactionList);
                merchantRepository.save(merchantEntity);

            }
        }
    }

    private Merchant createMerchantEntity(Map.Entry<UUID, List<TransactionXml>> merchant) {
        var m = merchant.getValue().get(0).getMerchant();
        return new Merchant(UUID.fromString(m.getUuid()), m.getName(), m.getDescription(),
                m.getEmail(), getStatus(m.getStatus()), m.getBankAccountSum(), null);
    }

    private static HashMap<UUID, List<TransactionXml>> unionTransactionsByMerchant(TransactionsXml transactionsXml) {
        var merchantTransactions = new HashMap<UUID, List<TransactionXml>>();

        for (var transactionXml : transactionsXml.getArticleList()) {
            var uuid = UUID.fromString(transactionXml.getMerchant().getUuid());
            merchantTransactions.putIfAbsent(uuid, new ArrayList<>());
            merchantTransactions.get(uuid).add(transactionXml);
        }
        return merchantTransactions;
    }

    private boolean isMerchantActive(int merchantStatus) {
        return MerchantStatus.ACTIV.ordinal() == merchantStatus;
    }

    private MerchantStatus getStatus(int merchantStatus) {
        return MerchantStatus.ACTIV.ordinal() == merchantStatus ? MerchantStatus.ACTIV : MerchantStatus.INACTIVE;
    }

    private BigDecimal calculateAccountsBalanceForRefunded(BigDecimal accountBalance, TransactionXml transaction) {
        accountBalance = accountBalance.subtract(transaction.getAmount());
        transaction.getCustomer().getBankAccountSum().add(transaction.getAmount());//fake
        return accountBalance;
    }

    private BigDecimal calculateAccountsBalanceForApproved(BigDecimal accountBalance, TransactionXml transaction) {
        accountBalance = accountBalance.add(transaction.getAmount());
        transaction.getCustomer().getBankAccountSum().subtract(transaction.getAmount());//fake
        return accountBalance;
    }

    private static void addTransactionToMerchant(TransactionXml transaction, TransactionStatus status, Merchant merchant, ArrayList<Transaction> transactionList) {
        Transaction transactionEntity = new Transaction(UUID.fromString(transaction.getUuid()), transaction.getAmount(),
                status, transaction.getCustomer().getEmail(),
                transaction.getCustomer().getPhone(), LocalDateTime.now(), merchant);

        transactionList.add(transactionEntity);
    }
}
