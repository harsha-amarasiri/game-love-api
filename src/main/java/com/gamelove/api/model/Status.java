package com.gamelove.api.model;

public enum Status {

    ACTIVE("Active", "Game is currently active"),

    INACTIVE("Inactive", "Game is temporarily inactive"),

    SUSPENDED("Suspended", "Game has been suspended");

    private final String displayName;
    private final String description;

    Status(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

}
