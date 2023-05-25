package org.example.xml.facada;

import lombok.RequiredArgsConstructor;
import org.example.model.Merchant;
import org.example.model.MerchantStatus;
import org.example.model.Transaction;
import org.example.model.TransactionStatus;
import org.example.repositories.MerchantRepository;
import org.example.xml.parser.MerchantXml;
import org.example.xml.parser.TransactionXml;
import org.example.xml.parser.TransactionsXml;
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

        HashMap<UUID, List<TransactionXml>> merchantTransactionsList = unionTransactionsByMerchant(transactionsXml);

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

    private Merchant createMerchantEntity(MerchantXml merchantXml) {
        return new Merchant(UUID.fromString(merchantXml.getUuid()), merchantXml.getName(), merchantXml.getDescription(),
                merchantXml.getEmail(), getStatus(merchantXml.getStatus()), merchantXml.getBankAccountSum(), null);
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

    private boolean isMerchantActive(int status) {
        return MerchantStatus.ACTIV.ordinal() == status;
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
