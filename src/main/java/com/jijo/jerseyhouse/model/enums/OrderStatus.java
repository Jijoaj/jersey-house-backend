package com.jijo.jerseyhouse.model.enums;

public enum OrderStatus {
    CREATED("CREATED"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED");

    public final String orderStatus;

    OrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
