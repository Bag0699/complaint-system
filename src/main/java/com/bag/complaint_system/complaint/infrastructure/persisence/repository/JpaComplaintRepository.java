package com.bag.complaint_system.complaint.infrastructure.persisence.repository;

import com.bag.complaint_system.complaint.domain.model.aggregate.Complaint;
import com.bag.complaint_system.complaint.infrastructure.persisence.entity.ComplaintEntity;
import com.bag.complaint_system.identity.infrastructure.persistence.entity.UserEntity;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface JpaComplaintRepository extends JpaRepository<ComplaintEntity, Long> {

    List<ComplaintEntity> findByVictimId(Long victimId);

    List<ComplaintEntity> findAllByStatus(ComplaintEntity.StatusEntity status);

    List<ComplaintEntity> findAllByViolenceType(ComplaintEntity.ViolenceTypeEntity violenceType);

    @Query("SELECT c FROM ComplaintEntity c WHERE c.createdAt BETWEEN :startDate AND :endDate")
    List<ComplaintEntity> findByDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    Long countByStatus(ComplaintEntity.StatusEntity status);

    Long countByViolenceType(ComplaintEntity.ViolenceTypeEntity violenceType);

}
