package com.bag.complaint_system.identity.application.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private String role;
  private String createdAt;
  private String updatedAt;
}
