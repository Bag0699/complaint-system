package com.bag.complaint_system.complaint.application.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintDetailResponse {

    private Long id;
    private Long victimId;
    private String victimName;
    private String victimEmail;
    private String description;
    private String status;
    private String violenceType;
    private LocalDate incidentDate;
    private String incidentLocation;
    private AggressorResponse aggressor;
    private List<EvidenceResponse> evidences;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
