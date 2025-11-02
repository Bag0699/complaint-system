package com.bag.complaint_system.identity.application.useCase;

import com.bag.complaint_system.identity.application.dto.request.UpdateProfileRequest;
import com.bag.complaint_system.identity.application.dto.response.AuthResponse;
import com.bag.complaint_system.identity.application.mapper.UserMapper;
import com.bag.complaint_system.identity.domain.model.aggregate.User;
import com.bag.complaint_system.identity.domain.model.valueObject.Phone;
import com.bag.complaint_system.identity.domain.repository.UserRepository;
import com.bag.complaint_system.identity.infrastructure.security.CustomUserDetails;
import com.bag.complaint_system.shared.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateProfileUseCase {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public AuthResponse execute(Long id, UpdateProfileRequest request) {

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("User not authenticated");
        }

        var principal = authentication.getPrincipal();
        CustomUserDetails userDeaDetails = (CustomUserDetails) principal;

        Long authenticatedId = userDeaDetails.getUserId();
        String role = userDeaDetails.getAuthorities().iterator().next().getAuthority();

        if (!role.equals("ROLE_ADMIN") && !authenticatedId.equals(id)) {
            throw new SecurityException("You are not allowed to update another user's profile");
        }

        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        Phone phone = new Phone(request.getPhone());
        user.updateProfile(
                request.getFirstName(),
                request.getLastName(),
                phone);
        User updatedUser = userRepository.save(user);
        return userMapper.toAuthResponse(updatedUser, null);
    }
}
