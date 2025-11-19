package com.bag.complaint_system.identity.infrastructure.rest;

import com.bag.complaint_system.identity.application.dto.request.RegisterUserRequest;
import com.bag.complaint_system.identity.application.dto.request.UpdateProfileRequest;
import com.bag.complaint_system.identity.application.dto.response.AuthResponse;
import com.bag.complaint_system.identity.application.dto.response.UserProfileResponse;
import com.bag.complaint_system.identity.application.dto.response.UserResponse;
import com.bag.complaint_system.identity.application.useCase.CreateAdminUseCase;
import com.bag.complaint_system.identity.application.useCase.GetAllUsersCase;
import com.bag.complaint_system.identity.application.useCase.GetProfileUseCase;
import com.bag.complaint_system.identity.application.useCase.UpdateProfileUseCase;
import com.bag.complaint_system.shared.infrastructure.security.SecurityContextHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final CreateAdminUseCase createAdminUseCase;
    private final UpdateProfileUseCase updateProfileUseCase;
    private final GetProfileUseCase getProfileUseCase;
    private final GetAllUsersCase getAllUsersCase;
    private final SecurityContextHelper securityContextHelper;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile() {
        UserProfileResponse response = getProfileUseCase.execute();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin")
    public ResponseEntity<AuthResponse> createAdmin(
            @Valid @RequestBody RegisterUserRequest request) {
        AuthResponse response = createAdminUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthResponse> updateProfile(
            @Valid @RequestBody UpdateProfileRequest request,
            @PathVariable Long id
    ) {
        AuthResponse response = updateProfileUseCase.execute(id, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        Long authenticatedUserId = securityContextHelper.getAuthenticatedUserId();
        return getAllUsersCase.execute(authenticatedUserId);
    }
}
