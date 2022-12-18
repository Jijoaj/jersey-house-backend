package com.jijo.jerseyhouse.model.enums;

public enum OrderStatus {
    CREATED("CREATED"),
    CONFIRMED("CONFIRMED"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED"),
    FAILED("FAILED");

    public final String orderStatus;

    OrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
