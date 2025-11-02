package com.bag.complaint_system.complaint.application.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AggressorResponse {
    private Long id;
    private String fullName;
    private String relationship;
    private String additionalDetails;
}
