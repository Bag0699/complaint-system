package com.bag.complaint_system.support.application.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupportCenterResponse {
  private Long id;
  private String name;
  private String street;
  private String district;
  private String fullAddress;
  private String phone;
  private String email;
  private String schedule;
  private boolean isActive;
  private LocalDateTime createdAt;
}
