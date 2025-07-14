package com.example.bankcards.util;

public enum CardStatusEnum {
    ACTIVE("Активна"),
    BLOCKED("Заблокирована");

    private final String description;

    CardStatusEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
