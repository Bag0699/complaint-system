package com.bag.complaint_system.analytics.application.useCase;

import com.bag.complaint_system.analytics.application.dto.response.ComplaintByStatusResponse;
import com.bag.complaint_system.analytics.domain.repository.AnalyticsRepository;
import com.bag.complaint_system.identity.domain.model.aggregate.User;
import com.bag.complaint_system.identity.domain.repository.UserRepository;
import com.bag.complaint_system.shared.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class GetComplaintsByStatusUseCase {

  private final AnalyticsRepository analyticsRepository;
  private final UserRepository userRepository;

  public ComplaintByStatusResponse execute(Long authId) {
    User user = userRepository.findById(authId).orElseThrow(UserNotFoundException::new);

    if (!user.isAdmin()) {
      throw new IllegalArgumentException("Only admins can view complaints by status");
    }

    Map<String, Long> data = analyticsRepository.countComplaintByStatus();

    Long total = data.values().stream().mapToLong(Long::longValue).sum();

    return ComplaintByStatusResponse.builder().data(data).totalComplaints(total).build();
  }
}
