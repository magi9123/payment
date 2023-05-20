package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {

    private final T response;
    private final Error exception;

    public static <T> ResponseDto<T> response(T content) {
        return new ResponseDto<>(content, null);
    }

    public static ResponseDto<Void> fail(String code, String message) {
        return new ResponseDto<>(null, new Error(code, message));
    }

    @Getter
    @RequiredArgsConstructor
    public static class Error {
        private final String code;
        private final String message;
    }
}
