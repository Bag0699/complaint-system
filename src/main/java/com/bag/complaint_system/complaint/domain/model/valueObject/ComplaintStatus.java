package com.bag.complaint_system.complaint.domain.model.valueObject;

import lombok.Getter;

@Getter
public enum ComplaintStatus {

    RECEIVED("Received", "Complaint has been received and registered"),
    IN_REVIEW("In Review", "Complaint is being reviewed by authorities"),
    ACTION_TAKEN("Action Taken", "Action has been taken on the complaint"),
    CLOSED("Closed", "Complaint has been closed");

    private final String displayName;
    private final String description;

    ComplaintStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public boolean isClosed() {
        return this == CLOSED;
    }

    public boolean canTransitionTo(ComplaintStatus newStatus) {
        // Define valid state transitions
        return switch (this) {
            case RECEIVED -> newStatus == IN_REVIEW || newStatus == CLOSED;
            case IN_REVIEW -> newStatus == ACTION_TAKEN || newStatus == CLOSED;
            case ACTION_TAKEN -> newStatus == CLOSED;
            case CLOSED -> false; // Cannot transition from CLOSED
        };
    }
}
