package com.bag.complaint_system.complaint.domain.model.valueObject;

import lombok.Getter;

@Getter
public enum VictimRelationship {
    FRIEND("Friend", "The aggressor is your friend"),
    FAMILY("Family", "The aggressor is your family member"),
    NEIGHBOUR("Neighbour", "The aggressor is your neighbour"),
    EX_PARTNER("Ex Partner", "The aggressor is your ex partner"),
    PARTNER("Partner", "The aggressor is your partner"),
    STRANGE("Strange", "The aggressor is strange"),
    OTHER("Other", "The aggressor does not fit any of the predefined categories");

    private final String displayName;
    private final String description;

    VictimRelationship(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
}
