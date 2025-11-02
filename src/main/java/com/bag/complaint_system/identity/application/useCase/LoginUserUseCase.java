package com.bag.complaint_system.identity.application.useCase;

import com.bag.complaint_system.identity.application.dto.request.LoginRequest;
import com.bag.complaint_system.identity.application.dto.response.AuthResponse;
import com.bag.complaint_system.identity.application.mapper.UserMapper;
import com.bag.complaint_system.identity.domain.model.aggregate.User;
import com.bag.complaint_system.identity.domain.model.valueObject.Email;
import com.bag.complaint_system.identity.domain.repository.UserRepository;
import com.bag.complaint_system.identity.domain.service.PasswordEncryptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUserUseCase {

  private final UserRepository userRepository;
  private final PasswordEncryptionService passwordService;
  private final GenerateTokenService generateTokenUseCase;
  private final UserMapper userMapper;

  public AuthResponse execute(LoginRequest request) {
    Email email = new Email(request.getEmail());

    User user =
        userRepository
            .findByEmail(email)
            .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

    boolean passwordMatches =
        passwordService.checkPassword(request.getPassword(), user.getPassword());
    if (!passwordMatches) {
      throw new BadCredentialsException("Invalid email or password");
    }

    String token = generateTokenUseCase.execute(user);
    return userMapper.toAuthResponse(user, token);
  }
}
