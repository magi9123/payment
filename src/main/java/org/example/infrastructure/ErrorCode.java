package org.example.infrastructure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    SERVER_ERROR("1", "Server error"),
    ENTITY_NOT_FOUND("2", "Merchant with id: {0} does not exist"),
    MERSHANT_TRANSACTION("3", "Merchant with id: {0} has related transactions.");


    private final String code;
    private final String message;

    public String toMessage(Object... args) {
        return MessageFormat.format(message, args);
    }
}