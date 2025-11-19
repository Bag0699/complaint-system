package com.bag.complaint_system.identity.application.useCase;

import com.bag.complaint_system.identity.application.dto.response.UserProfileResponse;
import com.bag.complaint_system.identity.application.dto.response.UserResponse;
import com.bag.complaint_system.identity.application.mapper.UserMapper;
import com.bag.complaint_system.identity.domain.model.aggregate.User;
import com.bag.complaint_system.identity.domain.repository.UserRepository;
import com.bag.complaint_system.shared.domain.exception.RoleAccessDeniedException;
import com.bag.complaint_system.shared.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllUsersCase {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Transactional(readOnly = true)
  public List<UserResponse> execute(Long authId) {

    User authUser = userRepository.findById(authId).orElseThrow(UserNotFoundException::new);

    if (!authUser.isAdmin()) {
      throw new RoleAccessDeniedException("Only admins can view all complaints");
    }

    List<User> users = userRepository.findAll();

    return users.stream().map(userMapper::toUserResponse).toList();
  }
}
