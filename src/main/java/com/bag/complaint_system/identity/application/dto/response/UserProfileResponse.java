package com.bag.complaint_system.identity.application.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {

    private String fullName;
    private String email;
    private String phone;
    private String role;
}
