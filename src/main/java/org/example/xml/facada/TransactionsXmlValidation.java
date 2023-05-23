package org.example.xml.facada;

import lombok.extern.slf4j.Slf4j;
import org.example.model.TransactionStatus;
import org.example.xml.TransactionXml;
import org.example.xml.TransactionsXml;

import java.util.regex.Pattern;

@Slf4j
public class TransactionsXmlValidation {
    private static final String REGEX_EMAIL = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final String REGEX_PHONE = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";

    public void validateXml(TransactionsXml transactionsXml) {

        for (var transaction : transactionsXml.getArticleList()) {

            transactionHasAmount(transaction);

            validateEmail(transaction.getCustomer().getEmail(), "Customer email is not valid.");
            validateEmail(transaction.getMerchant().getEmail(), "Merchant email is not valid.");

            validatePhone(transaction.getCustomer().getPhone(), "Customer phone is not valid.");
        }

    }

    private static void transactionHasAmount(TransactionXml transaction) {
        if (transaction.getAmount().signum() <= 0) {
            transaction.setType(TransactionStatus.ERROR.ordinal());
            log.error("Transaction is invalid");
        }
    }

    private void validateEmail(String email, String msg) {
        boolean isValid = Pattern.compile(REGEX_EMAIL).matcher(email).matches();
        if (!isValid) {
            log.warn(msg);
        }
    }

    private void validatePhone(String phone, String msg) {
        boolean isValid = Pattern.compile(REGEX_PHONE).matcher(phone).matches();
        if (!isValid) {
            log.warn(msg);
        }
    }
}
