package com.jijo.jerseyhouse.exception;

import com.jijo.jerseyhouse.exception.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerConfig {

    @ExceptionHandler({CommonInternalException.class})
    public ResponseEntity<ErrorResponse> commonException(Exception exception){
        String message = "invalid";
        HttpStatus httpStatus= HttpStatus.INTERNAL_SERVER_ERROR;
        if(exception instanceof CommonInternalException){
            httpStatus= ((CommonInternalException) exception).getHttpCode();
            message= ((CommonInternalException) exception).getExceptionMessage();
        }
        return new ResponseEntity<>(new ErrorResponse(httpStatus.toString(), message), httpStatus);
    }

}
