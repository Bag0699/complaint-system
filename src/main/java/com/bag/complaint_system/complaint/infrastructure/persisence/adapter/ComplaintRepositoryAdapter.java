package com.bag.complaint_system.complaint.infrastructure.persisence.adapter;

import com.bag.complaint_system.complaint.domain.model.aggregate.Complaint;
import com.bag.complaint_system.complaint.domain.model.entity.Aggressor;
import com.bag.complaint_system.complaint.domain.model.entity.Evidence;
import com.bag.complaint_system.complaint.domain.model.valueObject.ComplaintStatus;
import com.bag.complaint_system.complaint.domain.model.valueObject.VictimRelationship;
import com.bag.complaint_system.complaint.domain.model.valueObject.ViolenceType;
import com.bag.complaint_system.complaint.domain.repository.ComplaintRepository;
import com.bag.complaint_system.complaint.infrastructure.persisence.entity.AggressorEntity;
import com.bag.complaint_system.complaint.infrastructure.persisence.entity.ComplaintEntity;
import com.bag.complaint_system.complaint.infrastructure.persisence.entity.EvidenceEntity;
import com.bag.complaint_system.complaint.infrastructure.persisence.repository.JpaComplaintRepository;
import com.bag.complaint_system.identity.infrastructure.persistence.entity.UserEntity;
import com.bag.complaint_system.identity.infrastructure.persistence.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ComplaintRepositoryAdapter implements ComplaintRepository {

    private final JpaComplaintRepository jpaComplaintRepository;

    @Override
    public Complaint save(Complaint complaint) {
        ComplaintEntity complaintEntity = this.toComplaintEntity(complaint);
        return this.toComplaint(jpaComplaintRepository.save(complaintEntity));
    }

    @Override
    public Optional<Complaint> findById(Long id) {
        return jpaComplaintRepository.findById(id)
                .map(this::toComplaint);
    }

    @Override
    public List<Complaint> findByVictimId(Long victimId) {
        return jpaComplaintRepository.findByVictimId(victimId)
                .stream()
                .map(this::toComplaint)
                .collect(Collectors.toList());
    }

    @Override
    public List<Complaint> findAll() {
        return jpaComplaintRepository.findAll()
                .stream()
                .map(this::toComplaint)
                .collect(Collectors.toList());
    }

    @Override
    public List<Complaint> findAllByStatus(String status) {
        ComplaintEntity.StatusEntity statusEntity = ComplaintEntity.StatusEntity.valueOf(status);
        return jpaComplaintRepository.findAllByStatus(statusEntity)
                .stream()
                .map(this::toComplaint)
                .collect(Collectors.toList());
    }

    @Override
    public List<Complaint> findAllByViolenceType(String violenceType) {
        ComplaintEntity.ViolenceTypeEntity violenceTypeEntity = ComplaintEntity.ViolenceTypeEntity.valueOf(violenceType);
        return jpaComplaintRepository.findAllByViolenceType(violenceTypeEntity)
                .stream()
                .map(this::toComplaint)
                .collect(Collectors.toList());
    }

    @Override
    public List<Complaint> findAllByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return jpaComplaintRepository.findByDateRange(startDate, endDate)
                .stream()
                .map(this::toComplaint)
                .collect(Collectors.toList());
    }

    @Override
    public Long countByStatus(String status) {
        ComplaintEntity.StatusEntity complaintStatus = ComplaintEntity.StatusEntity.valueOf(status);
        return jpaComplaintRepository.countByStatus(complaintStatus);
    }

    @Override
    public Long countByViolenceType(String violenceType) {
        ComplaintEntity.ViolenceTypeEntity violenceTypeEntity = ComplaintEntity.ViolenceTypeEntity.valueOf(violenceType);
        return jpaComplaintRepository.countByViolenceType(violenceTypeEntity);
    }

    @Override
    public Long countAll() {
        return jpaComplaintRepository.count();
    }

    private ComplaintEntity toComplaintEntity(Complaint complaint) {
        ComplaintEntity complaintEntity = new ComplaintEntity();

        complaintEntity.setId(complaint.getId());
        complaintEntity.setVictimId(complaint.getVictimId());
        complaintEntity.setDescription(complaint.getDescription());
        complaintEntity.setStatus(ComplaintEntity.StatusEntity.valueOf(complaint.getStatus().name()));
        complaintEntity.setViolenceType(ComplaintEntity.ViolenceTypeEntity.valueOf(complaint.getViolenceType().name()));
        complaintEntity.setIncidentDate(complaint.getIncidentDate());
        complaintEntity.setIncidentLocation(complaint.getIncidentLocation());
        complaintEntity.setCreatedAt(complaint.getCreatedAt());
        complaintEntity.setUpdatedAt(complaint.getUpdatedAt());

        if (complaint.getAggressor() != null) {
            AggressorEntity aggressorEntity = toAggressorEntity(complaint.getAggressor());
            complaintEntity.setAggressor(aggressorEntity);
        }

        List<EvidenceEntity> evidenceEntityList = complaint.getEvidences().stream()
                .map(this::toEvidenceEntity)
                .collect(Collectors.toList());

        evidenceEntityList.forEach(complaintEntity::addEvidence);
        return complaintEntity;
    }

    private Complaint toComplaint(ComplaintEntity complaintEntity) {

        Aggressor aggressor = complaintEntity.getAggressor() != null
                ? this.toAggressor(complaintEntity.getAggressor())
                : null;

        Complaint complaint = Complaint.create(
                complaintEntity.getVictimId(),
                complaintEntity.getDescription(),
                ViolenceType.valueOf(complaintEntity.getViolenceType().name()),
                complaintEntity.getIncidentDate(),
                complaintEntity.getIncidentLocation(),
                aggressor
        );
        complaint.setId(complaintEntity.getId());
        complaint.setStatus(ComplaintStatus.valueOf(complaintEntity.getStatus().name()));
        complaint.setCreatedAt(complaintEntity.getCreatedAt());
        complaint.setUpdatedAt(complaintEntity.getUpdatedAt());

        if (complaintEntity.getEvidences() != null) {
            List<Evidence> evidences = complaintEntity.getEvidences().stream()
                    .map(this::toEvidence)
                    .collect(Collectors.toList());
            complaint.setEvidences(evidences);
        }
        return complaint;
    }

    private Aggressor toAggressor(AggressorEntity aggressorEntity) {
        Aggressor aggressor = Aggressor.create(
                aggressorEntity.getFullName(),
                VictimRelationship.valueOf(aggressorEntity.getRelationship().name()),
                aggressorEntity.getAdditionalDetails()
        );
        aggressor.setId(aggressorEntity.getId());
        return aggressor;
    }

    private AggressorEntity toAggressorEntity(Aggressor aggressor) {
        AggressorEntity aggressorEntity = new AggressorEntity();
        aggressorEntity.setId(aggressor.getId());
        aggressorEntity.setFullName(aggressor.getFullName());
        aggressorEntity.setRelationship(AggressorEntity.RelationshipEntity.valueOf(aggressor.getRelationship().name()));
        aggressorEntity.setAdditionalDetails(aggressor.getAdditionalDetails());
        return aggressorEntity;
    }

    private Evidence toEvidence(EvidenceEntity evidenceEntity) {
        if (evidenceEntity == null) {
            return null;
        }
        Evidence evidence = Evidence.create(
                evidenceEntity.getFileName(),
                evidenceEntity.getFilePath(),
                evidenceEntity.getFileType(),
                evidenceEntity.getFileSize()
        );
        evidenceEntity.setId(evidence.getId());
        evidenceEntity.setUploadedAt(evidence.getUploadedAt());
        return evidence;
    }

    private EvidenceEntity toEvidenceEntity(Evidence evidence) {
        EvidenceEntity evidenceEntity = new EvidenceEntity();
        evidenceEntity.setId(evidence.getId());
        evidenceEntity.setFileName(evidence.getFileName());
        evidenceEntity.setFilePath(evidence.getFilePath());
        evidenceEntity.setFileType(evidence.getFileType());
        evidenceEntity.setFileSize(evidence.getFileSize());
        evidenceEntity.setUploadedAt(evidence.getUploadedAt());
        return evidenceEntity;
    }
}
