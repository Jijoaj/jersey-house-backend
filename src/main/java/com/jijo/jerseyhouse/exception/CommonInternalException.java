package com.jijo.jerseyhouse.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public final class CommonInternalException extends Exception {
    final HttpStatus httpCode;
    final String exceptionMessage;

    public CommonInternalException(HttpStatus httpCode, String message) {
        this.httpCode = httpCode;
        this.exceptionMessage = message;
    }

    public CommonInternalException(String message) {
        this.exceptionMessage = message;
        this.httpCode= HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
