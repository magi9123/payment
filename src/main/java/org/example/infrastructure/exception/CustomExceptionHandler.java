package org.example.infrastructure.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ResponseDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@Slf4j
@Component
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CodedException.class)
    public ResponseEntity<ResponseDto<Void>> handlerCodedException(CodedException exception) {
        log.error(exception.getMessage(), exception);
        return getResponse(HttpStatus.OK, ResponseDto.fail(String.valueOf(exception.getCode()), exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<Void>> handlerGeneralException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return getResponse(HttpStatus.INTERNAL_SERVER_ERROR, ResponseDto.fail(ErrorCode.SERVER_ERROR.getCode(), ex.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errors = getErrors(ex);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
                .body(ResponseDto.fail(ErrorCode.BAD_REQUEST.getCode(), errors));
    }

    private ResponseEntity<ResponseDto<Void>> getResponse(HttpStatus status, ResponseDto<Void> content) {
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(content);
    }

    private String getErrors(MethodArgumentNotValidException ex) {
        String fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(System.lineSeparator()));

        StringBuilder errors = new StringBuilder();
        errors.append("Something goes wrong check:").append(System.lineSeparator()).append(fieldErrors);

        return errors.toString();
    }
}
