package com.bag.complaint_system.complaint.domain.repository;

import com.bag.complaint_system.complaint.domain.model.aggregate.Complaint;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ComplaintRepository {

    Complaint save(Complaint complaint);

    Optional<Complaint> findById(Long id);

    List<Complaint> findByVictimId(Long victimId);

    List<Complaint> findAll();

    List<Complaint> findAllByStatus(String status);

    List<Complaint> findAllByViolenceType(String violenceType);

    List<Complaint> findAllByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    Long countByStatus(String status);

    Long countByViolenceType(String violenceType);

    Long countAll();
}
