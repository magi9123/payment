package org.example.file.facada;

import lombok.extern.slf4j.Slf4j;
import org.example.model.TransactionStatus;
import org.example.file.ValidationError;
import org.example.file.parser.TransactionParser;
import org.example.file.parser.TransactionsParser;

import java.util.regex.Pattern;

@Slf4j
public class TransactionsFileValidation {
    public static final String REGEX_EMAIL = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    public static final String REGEX_PHONE = "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$";

    private static final String REGEX_UUID = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    public void validateData(TransactionsParser transactionsParser) {
        ValidationError.getValidationError().clearMessages();

        for (var transaction : transactionsParser.getArticleList()) {

            transactionHasAmount(transaction);

            validateUUID(transaction.getUuid(), "Transaction with wrong uuid - ");
            validateUUID(transaction.getMerchant().getUuid(), "Merchant with wrong uuid - ");

            validateEmail(transaction.getCustomer().getEmail(), "Customer email is not valid -");
            validateEmail(transaction.getMerchant().getEmail(), "Merchant email is not valid -");

            validatePhone(transaction.getCustomer().getPhone(), "Customer phone is not valid -");

            checkForExistingMessage();
        }

    }

    private void transactionHasAmount(TransactionParser transaction) {
        if (transaction.getAmount().signum() <= 0) {
            transaction.setType(TransactionStatus.REVERSED.ordinal());
            ValidationError.getValidationError().setError("Change status 'reversed' for transaction - " + transaction.getUuid());
        }
    }

    private void validateEmail(String email, String msg) {
        boolean isValid = Pattern.compile(REGEX_EMAIL).matcher(email).matches();
        if (!isValid) {
            ValidationError.getValidationError().setError(msg + email);
        }
    }

    private void validatePhone(String phone, String msg) {
        boolean isValid = Pattern.compile(REGEX_PHONE).matcher(phone).matches();
        if (!isValid) {
            ValidationError.getValidationError().setError(msg + phone);
        }
    }

    private void validateUUID(String uuid, String msg) {
        boolean isValid = Pattern.compile(REGEX_UUID).matcher(uuid).matches();
        if (!isValid) {
            ValidationError.getValidationError().setError(msg + uuid);
        }
    }

    private static void checkForExistingMessage() {
        if (ValidationError.getValidationError().getErrors().length() == 0) {
            ValidationError.getValidationError().setError("All transactions are saved.");
        }
    }
}
