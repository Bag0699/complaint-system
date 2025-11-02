package com.bag.complaint_system.complaint.infrastructure.persisence.entity;

import com.bag.complaint_system.identity.infrastructure.persistence.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "complaints")
public class ComplaintEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "victim_id", nullable = false)
//    private UserEntity victim;
    @Column(name = "victim_id", nullable = false)
    private Long victimId;

    private String description;

    @Enumerated(EnumType.STRING)
    private StatusEntity status = StatusEntity.RECEIVED;

    @Enumerated(EnumType.STRING)
    @Column(name = "violence_type")
    private ViolenceTypeEntity violenceType;

    @Column(name = "incident_date")
    private LocalDate incidentDate;

    @Column(name = "incident_location")
    private String incidentLocation;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "complaint", cascade = CascadeType.ALL, orphanRemoval = true)
    private AggressorEntity aggressor;

    @OneToMany(mappedBy = "complaint", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EvidenceEntity> evidences = new ArrayList<>();


    public void addEvidence(EvidenceEntity evidenceEntity) {
        evidences.add(evidenceEntity);
        evidenceEntity.setComplaint(this);
    }

    public void setAggressor(AggressorEntity aggressor) {
        this.aggressor = aggressor;
        if (aggressor != null) {
            aggressor.setComplaint(this);
        }
    }

    public enum StatusEntity {
        RECEIVED,
        IN_REVIEW,
        ACTION_TAKEN,
        CLOSED
    }

    public enum ViolenceTypeEntity {
        PHYSICAL,
        PSYCHOLOGICAL,
        EMOTIONAL,
        SOCIAL,
        HARASSMENT,
        ECONOMIC,
        SEXUAL,
        OTHER
    }
}
