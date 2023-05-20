package org.example.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ResponseDto;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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

    private ResponseEntity<ResponseDto<Void>> getResponse(HttpStatus status, ResponseDto<Void> content) {
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(content);
    }
}
