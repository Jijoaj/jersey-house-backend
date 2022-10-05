package com.jijo.jerseyhouse.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class CommonInternalException extends Exception {
    HttpStatus httpCode;
    String exceptionMessage;

    public CommonInternalException(HttpStatus httpCode, String message) {
        this.httpCode = httpCode;
        this.exceptionMessage = message;
    }

    public CommonInternalException(String message) {
        this.exceptionMessage = message;
        this.httpCode= HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
