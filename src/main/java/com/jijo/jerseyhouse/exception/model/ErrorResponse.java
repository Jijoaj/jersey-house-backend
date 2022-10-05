package com.jijo.jerseyhouse.exception.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    String errorCode;
    String message;
    Date time;

    public ErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
        this.time = Calendar.getInstance().getTime();
    }
}
