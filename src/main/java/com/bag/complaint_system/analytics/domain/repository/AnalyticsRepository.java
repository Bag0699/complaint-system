package com.bag.complaint_system.analytics.domain.repository;

import com.bag.complaint_system.analytics.domain.model.valueObject.DateRange;

import java.time.LocalDate;
import java.util.Map;

public interface AnalyticsRepository {

  Map<LocalDate, Long> countComplaintsByDate(DateRange dateRange);

  Map<String, Long> countComplaintsByViolenceType();

  Map<String, Long> countComplaintsByViolenceType(DateRange dateRange);

  Map<String, Long> countComplaintByStatus();

  Map<String, Long> countComplaintsByStatus(DateRange dateRange);

  Map<String, Long> countComplaintsByDistrict();

  Long countTotalComplaints();

  Long countComplaintsInDateRange(DateRange dateRange);

  Double getAverageResolutionTime();
}
