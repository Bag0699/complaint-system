package com.bag.complaint_system.identity.application.useCase;

import com.bag.complaint_system.identity.application.dto.request.RegisterUserRequest;
import com.bag.complaint_system.identity.application.dto.response.AuthResponse;
import com.bag.complaint_system.identity.application.mapper.UserMapper;
import com.bag.complaint_system.identity.domain.model.aggregate.User;
import com.bag.complaint_system.identity.domain.model.valueObject.Email;
import com.bag.complaint_system.identity.domain.model.valueObject.Phone;
import com.bag.complaint_system.identity.domain.repository.UserRepository;
import com.bag.complaint_system.identity.domain.service.PasswordEncryptionService;
import com.bag.complaint_system.shared.domain.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.bag.complaint_system.shared.util.ErrorCatalog.EMAIL_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
public class CreateAdminUseCase {

  private final UserRepository userRepository;
  private final PasswordEncryptionService passwordService;
  private final UserMapper userMapper;

  public AuthResponse execute(RegisterUserRequest request) {
    Email email = new Email(request.getEmail());
    if (userRepository.existsByEmail(email)) {
      throw new UserAlreadyExistsException(
          EMAIL_ALREADY_EXISTS.getMessage());
    }
    String hashedPassword = passwordService.encryptPassword(request.getPassword());
    Phone phone = new Phone(request.getPhone());

    User admin =
        User.createAdmin(
            request.getFirstName(), request.getLastName(), email, hashedPassword, phone);

    User savedAdmin = userRepository.save(admin);

    return userMapper.toAuthResponse(savedAdmin, null);
  }
}
