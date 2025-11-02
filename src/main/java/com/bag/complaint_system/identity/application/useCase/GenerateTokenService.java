package com.bag.complaint_system.identity.application.useCase;

import com.bag.complaint_system.identity.domain.model.aggregate.User;
import com.bag.complaint_system.identity.infrastructure.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenerateTokenService {

    private final JwtTokenProvider jwtTokenProvider;

    public String execute(User user) {
        return jwtTokenProvider.generateToken(
                user.getId(),
                user.getEmail().getValue(),
                user.getRole().name()
        );
    }
}
