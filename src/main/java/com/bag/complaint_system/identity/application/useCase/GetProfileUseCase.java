package com.bag.complaint_system.identity.application.useCase;

import com.bag.complaint_system.identity.application.dto.response.UserProfileResponse;
import com.bag.complaint_system.identity.application.mapper.UserMapper;
import com.bag.complaint_system.identity.domain.model.aggregate.User;
import com.bag.complaint_system.identity.domain.repository.UserRepository;
import com.bag.complaint_system.identity.infrastructure.security.CustomUserDetails;
import com.bag.complaint_system.shared.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetProfileUseCase {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public UserProfileResponse execute() {

    var authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      throw new SecurityException("User not authenticated");
    }

    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

    Long userId = userDetails.getUserId();

    User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

    return userMapper.toUserProfileResponse(user);
  }
}
