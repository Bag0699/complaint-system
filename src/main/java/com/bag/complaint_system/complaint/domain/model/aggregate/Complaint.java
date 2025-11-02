package com.bag.complaint_system.complaint.domain.model.aggregate;

import com.bag.complaint_system.complaint.domain.model.entity.Aggressor;
import com.bag.complaint_system.complaint.domain.model.entity.Evidence;
import com.bag.complaint_system.complaint.domain.model.valueObject.ComplaintStatus;
import com.bag.complaint_system.complaint.domain.model.valueObject.ViolenceType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class Complaint {
    private static final int MAX_EVIDENCES = 10;

    private Long id;
    private Long victimId;
    private String description;
    private ComplaintStatus status;
    private ViolenceType violenceType;
    private LocalDate incidentDate;
    private String incidentLocation;
    private Aggressor aggressor;
    private List<Evidence> evidences;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Complaint() {
        this.evidences = new ArrayList<>();
    }

    public static Complaint create(
            Long victimId,
            String description,
            ViolenceType violenceType,
            LocalDate incidentDate,
            String incidentLocation,
            Aggressor aggressor
    ) {
        validateVictimId(victimId);
        validateDescription(description);
        validateIncidentDate(incidentDate);
        validateAggressor(aggressor);
        validateIncidentLocation(incidentLocation);

        Complaint complaint = new Complaint();
        complaint.victimId = victimId;
        complaint.description = description.trim();
        complaint.status = ComplaintStatus.RECEIVED;
        complaint.violenceType = violenceType;
        complaint.incidentDate = incidentDate;
        complaint.incidentLocation = incidentLocation != null ? incidentLocation.trim() : null;
        complaint.aggressor = aggressor;
        complaint.createdAt = LocalDateTime.now();
        complaint.updatedAt = LocalDateTime.now();

        return complaint;
    }

    public void updateStatus(ComplaintStatus newStatus) {
        if (this.status == newStatus) {
            throw new IllegalArgumentException("Complaint is already in " + newStatus + " status");
        }

        if (!this.status.canTransitionTo(newStatus)) {
            throw new IllegalArgumentException(
                    "Cannot transition from " + this.status + " to " + newStatus
            );
        }

        this.status = newStatus;
        this.updatedAt = LocalDateTime.now();
    }

    public void addEvidence(Evidence evidence) {
        if (this.status == ComplaintStatus.CLOSED) {
            throw new IllegalArgumentException("Cannot add evidence to a closed complaint");
        }

        if (this.evidences.size() >= MAX_EVIDENCES) {
            throw new IllegalArgumentException(
                    "Cannot add more than " + MAX_EVIDENCES + " evidences per complaint"
            );
        }

        this.evidences.add(evidence);
        this.updatedAt = LocalDateTime.now();
    }

    public List<Evidence> getEvidences() {
        return Collections.unmodifiableList(evidences);
    }

    public boolean belongsToVictim(Long victimId) {
        return this.victimId.equals(victimId);
    }

    public boolean isClosed() {
        return this.status == ComplaintStatus.CLOSED;
    }

    public int getEvidenceCount() {
        return this.evidences.size();
    }

    private static void validateVictimId(Long victimId) {
        if (victimId == null || victimId <= 0) {
            throw new IllegalArgumentException("Invalid victim ID");
        }
    }

    private static void validateIncidentDate(LocalDate incidentDate) {
        if (incidentDate == null) {
            throw new IllegalArgumentException("Incident date cannot be null");
        }
        if (incidentDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Incident date cannot be in the future");
        }
    }

    private static void validateDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Complaint description cannot be empty");
        }
        if (description.trim().length() < 20) {
            throw new IllegalArgumentException("Complaint description must be at least 20 characters");
        }
    }

    private static void validateAggressor(Aggressor aggressor) {
        if (aggressor == null) {
            throw new IllegalArgumentException("Aggressor cannot be null");
        }
    }

    private static void validateIncidentLocation(String incidentLocation) {
        if (incidentLocation == null || incidentLocation.trim().isEmpty()) {
            throw new IllegalArgumentException("Incident location cannot be empty");
        }
        if (incidentLocation.trim().length() > 200) {
            throw new IllegalArgumentException("Incident location cannot exceed 200 characters");
        }
    }

    public void setEvidences(List<Evidence> evidences) {
        this.evidences = evidences != null ? evidences : new ArrayList<>();
    }
}
