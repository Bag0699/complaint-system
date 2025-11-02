package com.bag.complaint_system.complaint.domain.model.valueObject;

import lombok.Getter;

@Getter
public enum ViolenceType {
    PHYSICAL("Physical", "The type of violence is physical"),
    PSYCHOLOGICAL("Psychological", "The type of violence is psychological"),
    EMOTIONAL("Emotional", "The type of violence is emotional"),
    SOCIAL("Social", "The type of violence is social"),
    HARASSMENT("Harassment", "The type of violence is harassment"),
    ECONOMIC("Economic", "The type of violence is economic"),
    SEXUAL("Sexual", "The type of violence is sexual"),
    OTHER("Other", "Other type violence");
    private final String displayName;
    private final String description;

    ViolenceType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
}
