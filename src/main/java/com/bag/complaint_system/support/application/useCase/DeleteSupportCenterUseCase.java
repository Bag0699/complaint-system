package com.bag.complaint_system.support.application.useCase;

import com.bag.complaint_system.identity.domain.model.aggregate.User;
import com.bag.complaint_system.identity.domain.repository.UserRepository;
import com.bag.complaint_system.shared.domain.exception.RoleAccessDeniedException;
import com.bag.complaint_system.shared.domain.exception.SupportCenterNotFoundException;
import com.bag.complaint_system.support.domain.model.aggregate.SupportCenter;
import com.bag.complaint_system.support.domain.repository.SupportCenterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteSupportCenterUseCase {

  private final SupportCenterRepository supportCenterRepository;
  private final UserRepository userRepository;

  @Transactional
  public void execute(Long centerId, Long authId) {

    User authenticatedUser =
        userRepository
            .findById(authId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

    if (!authenticatedUser.isAdmin()) {
      throw new RoleAccessDeniedException("Only admins can delete support centers");
    }

    SupportCenter center =
        supportCenterRepository.findById(centerId).orElseThrow(SupportCenterNotFoundException::new);

    center.deactivate();
    supportCenterRepository.save(center);
  }
}
