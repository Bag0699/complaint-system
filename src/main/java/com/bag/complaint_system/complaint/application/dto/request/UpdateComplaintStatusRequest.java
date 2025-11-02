package com.bag.complaint_system.complaint.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateComplaintStatusRequest {

    @NotBlank(message = "The field New status cannot be blank or null.")
    private String newStatus;

}
