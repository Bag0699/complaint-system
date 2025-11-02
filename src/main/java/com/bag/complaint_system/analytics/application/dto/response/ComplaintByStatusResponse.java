package com.bag.complaint_system.analytics.application.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.Map;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintByStatusResponse {

  private Map<String, Long> data;
  private Long totalComplaints;
}
