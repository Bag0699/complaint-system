package com.bag.complaint_system.complaint.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateComplaintRequest {

    @NotBlank(message = "Description is required")
    @Size(min = 20, max = 2000, message = "Description must be between 20 and 2000 characters")
    private String description;

    @NotNull(message = "Violence type is required")
    private String violenceType;

    @PastOrPresent(message = "Incident date cannot be in the future")
    private LocalDate incidentDate;

    @Size(max = 255, message = "Incident location cannot exceed 255 characters")
    private String incidentLocation;

    // Aggressor information
    @NotBlank(message = "Aggressor full name is required")
    @Size(min = 2, max = 200, message = "Aggressor full name must be between 2 and 200 characters")
    private String aggressorFullName;

    @NotNull(message = "Relationship with aggressor is required")
    private String aggressorRelationship;

    @Size(max = 1000, message = "Additional details cannot exceed 1000 characters")
    private String aggressorAdditionalDetails;
}
