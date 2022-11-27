package com.jijo.jerseyhouse.model.enums;

public enum JerseyViewSortBy {
    teamName("teamCode.teamName"),
    season("seasonCode.seasonCode");

    public String value;
    JerseyViewSortBy(String fieldName) {
        this.value = fieldName;
    }
}
