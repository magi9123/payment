package org.example.infrastructure.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    SERVER_ERROR("1", "Server error"),
    MERCHAT_NOT_FOUND("2", "Merchant with id: {0} does not exist"),
    MERSHANT_TRANSACTION("3", "Merchant with id: {0} has related transactions."),
    TRANSACTION_NOT_FOUND("3", "Transaction with id: {0} does not exist"),
    BAD_REQUEST("4", "Incorrect data: {0}"),
    FILE_IS_EMPTY("5", "File {0} is empty"),
    FILE_FORMAT_NOT_SUPPORTED("6", "File format is not supported - {0}");

    private final String code;
    private final String message;

    public String toMessage(Object... args) {
        return MessageFormat.format(message, args);
    }
}
