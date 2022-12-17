package com.jijo.jerseyhouse.model.enums;

public enum JerseyViewSortBy {
    teamName("teamCode.teamName"),
    season("seasonCode.seasonCode");

    public final String value;
    JerseyViewSortBy(String fieldName) {
        this.value = fieldName;
    }
}
