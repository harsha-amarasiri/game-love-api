package com.gamelove.api.model;

public enum Status {

    ACTIVE("Active", "Subject currently active"),

    INACTIVE("Inactive", "Subject has been marked as inactive"),

    SUSPENDED("Suspended", "Subject has been suspended");

    private final String displayName;
    private final String description;

    Status(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

}
