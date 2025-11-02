package com.bag.complaint_system.complaint.application.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintResponse {

  private Long id;
  private Long victimId;
  private String victimName;
  private String description;
  private String status;
  private String violenceType;
  private LocalDate incidentDate;
  private String incidentLocation;
  private int evidenceCount;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
