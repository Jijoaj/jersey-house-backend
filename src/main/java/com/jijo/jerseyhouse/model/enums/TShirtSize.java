package com.jijo.jerseyhouse.model.enums;

public enum TShirtSize {
    EXTRA_SMALL("xs"),
    SMALL("s"),
    MEDIUM("m"),
    LARGE("l"),
    EXTRA_LARGE("xl");

    public final String value;


    TShirtSize(String value) {
        this.value = value;
    }
}
