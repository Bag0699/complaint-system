package com.bag.complaint_system.analytics.application.useCase;

import com.bag.complaint_system.analytics.domain.repository.AnalyticsRepository;
import com.bag.complaint_system.identity.domain.model.aggregate.User;
import com.bag.complaint_system.identity.domain.repository.UserRepository;
import com.bag.complaint_system.shared.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAverageResolutionTimeUseCase {

  private final AnalyticsRepository analyticsRepository;
  private final UserRepository userRepository;

  public Double execute(Long authId) {
    User user = userRepository.findById(authId).orElseThrow(UserNotFoundException::new);

    if (!user.isAdmin()) {
      throw new IllegalArgumentException("Only admins can view average resolution time");
    }

    return analyticsRepository.getAverageResolutionTime();
  }
}
