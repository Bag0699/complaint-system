package com.bag.complaint_system.complaint.domain.model.entity;

import com.bag.complaint_system.complaint.domain.model.valueObject.VictimRelationship;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Aggressor {

    @Setter
    private Long id;

    private String fullName;
    private VictimRelationship relationship;
    private String additionalDetails;

    private Aggressor() {}

    public static Aggressor create(
            String fullName,
            VictimRelationship relationship,
            String additionalDetails
    ){
        validateFullName(fullName);
        validateRelationship(relationship);

        Aggressor aggressor = new Aggressor();
        aggressor.fullName = fullName.trim();
        aggressor.relationship = relationship;
        aggressor.additionalDetails = additionalDetails != null ? additionalDetails.trim() : null;
        return aggressor;
    }

    public void update(String fullName, VictimRelationship relationship, String additionalDetails){
        validateFullName(fullName);

        this.fullName = fullName.trim();
        this.relationship = relationship;
        this.additionalDetails = additionalDetails != null ? additionalDetails.trim() : null;
    }

    private static void validateFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Aggressor full name cannot be empty");
        }

        if (fullName.trim().length() > 200) {
            throw new IllegalArgumentException("Aggressor full name cannot exceed 200 characters");
        }
    }

    private static void validateRelationship(VictimRelationship relationship) {
        if (relationship == null) {
            throw new IllegalArgumentException("Relationship cannot be null");
        }
    }
}
