package com.bag.complaint_system.analytics.infrastructure.persistence.adapter;

import com.bag.complaint_system.analytics.domain.model.valueObject.DateRange;
import com.bag.complaint_system.analytics.domain.repository.AnalyticsRepository;
import com.bag.complaint_system.analytics.infrastructure.persistence.repository.JpaAnalyticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AnalyticsRepositoryAdapter implements AnalyticsRepository {

  private final JpaAnalyticsRepository jpaAnalyticsRepository;

  @Override
  public Map<LocalDate, Long> countComplaintsByDate(DateRange dateRange) {
    List<Object[]> results =
        jpaAnalyticsRepository.countComplaintsByDate(
            dateRange.getStartDate(), dateRange.getEndDate());

    Map<LocalDate, Long> map = new HashMap<>();
    for (Object[] result : results) {
      LocalDate date = ((Date) result[0]).toLocalDate();
      Long count = (Long) result[1];
      map.put(date, count);
    }
    return map;
  }

  @Override
  public Map<String, Long> countComplaintsByViolenceType() {
    List<Object[]> results = jpaAnalyticsRepository.countComplaintsByViolenceType();
    return converToMap(results);
  }

  @Override
  public Map<String, Long> countComplaintsByViolenceType(DateRange dateRange) {
    List<Object[]> results =
        jpaAnalyticsRepository.countComplaintsByViolenceTypeInDateRange(
            dateRange.getStartDate(), dateRange.getEndDate());

    return converToMap(results);
  }

  @Override
  public Map<String, Long> countComplaintByStatus() {
    List<Object[]> results = jpaAnalyticsRepository.countComplaintsByStatus();
    return converToMap(results);
  }

  @Override
  public Map<String, Long> countComplaintsByStatus(DateRange dateRange) {
    List<Object[]> results =
        jpaAnalyticsRepository.countComplaintsByStatusInDateRange(
            dateRange.getStartDate(), dateRange.getEndDate());
    return converToMap(results);
  }

  @Override
  public Map<String, Long> countComplaintsByDistrict() {
    return Map.of();
  }

  @Override
  public Long countTotalComplaints() {
    return jpaAnalyticsRepository.count();
  }

  @Override
  public Long countComplaintsInDateRange(DateRange dateRange) {
    List<Object[]> results =
        jpaAnalyticsRepository.countComplaintsByDate(
            dateRange.getStartDate(), dateRange.getEndDate());

    return results.stream().mapToLong(result -> (Long) result[0]).sum();
  }

  @Override
  public Double getAverageResolutionTime() {
    Double avg = jpaAnalyticsRepository.getAverageResolutionTime();
    return avg != null ? avg : 0.0;
  }

  private Map<String, Long> converToMap(List<Object[]> results) {
    Map<String, Long> map = new HashMap<>();
    for (Object[] result : results) {
      String key = result[0].toString();
      Long count = (Long) result[1];
      map.put(key, count);
    }
    return map;
  }
}
