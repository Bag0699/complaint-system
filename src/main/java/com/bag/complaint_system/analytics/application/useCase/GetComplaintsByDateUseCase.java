package com.bag.complaint_system.analytics.application.useCase;

import com.bag.complaint_system.analytics.application.dto.response.ComplaintByDateRangeResponse;
import com.bag.complaint_system.analytics.domain.model.valueObject.DateRange;
import com.bag.complaint_system.analytics.domain.repository.AnalyticsRepository;
import com.bag.complaint_system.identity.domain.model.aggregate.User;
import com.bag.complaint_system.identity.domain.repository.UserRepository;
import com.bag.complaint_system.shared.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GetComplaintsByDateUseCase {

  private final AnalyticsRepository analyticsRepository;
  private final UserRepository userRepository;

  public ComplaintByDateRangeResponse execute(Long authId, LocalDate startDate, LocalDate endDate) {

    User user = userRepository.findById(authId).orElseThrow(UserNotFoundException::new);

    if (!user.isAdmin()) {
      throw new IllegalArgumentException("Only admins can view complaints by date");
    }

    DateRange dateRange = DateRange.between(startDate, endDate);

    Map<LocalDate, Long> data = analyticsRepository.countComplaintsByDate(dateRange);

    Long total = data.values().stream().mapToLong(Long::longValue).sum();

    return ComplaintByDateRangeResponse.builder()
            .data(data)
            .totalComplaints(total)
            .startDate(startDate)
            .endDate(endDate)
            .build();
  }
}
