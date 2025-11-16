package com.bag.complaint_system.analytics.infrastructure.persistence.repository;

import com.bag.complaint_system.complaint.infrastructure.persisence.entity.ComplaintEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface JpaAnalyticsRepository extends JpaRepository<ComplaintEntity, Long> {

  @Query(
      """
        SELECT DATE(c.createdAt) as date, COUNT(c) as count
        FROM ComplaintEntity c
        WHERE c.createdAt BETWEEN :startDate AND :endDate
        GROUP BY DATE(c.createdAt)
        ORDER BY DATE(c.createdAt)
        """)
  List<Object[]> countComplaintsByDate(
      @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

  @Query(
      """
        SELECT c.violenceType, COUNT(c)
        FROM ComplaintEntity c
        GROUP BY c.violenceType
        """)
  List<Object[]> countComplaintsByViolenceType();

  @Query(
      """
        SELECT c.violenceType, COUNT(c)
        FROM ComplaintEntity c
        WHERE c.createdAt BETWEEN :startDate AND :endDate
        GROUP BY c.violenceType
        """)
  List<Object[]> countComplaintsByViolenceTypeInDateRange(
      @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

  @Query(
      """
        SELECT c.status, COUNT(c)
        FROM  ComplaintEntity c
        GROUP BY c.status
        """)
  List<Object[]> countComplaintsByStatus();

  @Query(
      """
        SELECT c.status, COUNT(c)
        FROM ComplaintEntity c
        WHERE c.createdAt BETWEEN :startDate AND :endDate
        GROUP BY c.status
        """)
  List<Object[]> countComplaintsByStatusInDateRange(
      @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

  @Query(
      value =
          """
      SELECT AVG(EXTRACT(DAY FROM (c.updated_at - c.created_at)))
      FROM complaints c
      WHERE c.status = 'CLOSED'
      """,
      nativeQuery = true)
  Double getAverageResolutionTime();
}
