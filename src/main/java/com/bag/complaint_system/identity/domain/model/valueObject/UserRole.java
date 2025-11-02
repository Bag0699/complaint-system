package com.bag.complaint_system.identity.domain.model.valueObject;

import lombok.Getter;

@Getter
public enum UserRole {

    VICTIM("Victim", "User who can file complaints"),
    ADMIN("Administrator", "User who can manage the system");

    private final String displayName;
    private final String description;

    UserRole(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public boolean isAdmin() {
        return this == ADMIN;
    }

    public boolean isVictim() {
        return this == VICTIM;
    }
}
