package com.bag.complaint_system.analytics.infrastructure.rest;

import com.bag.complaint_system.analytics.application.dto.response.ComplaintByDateRangeResponse;
import com.bag.complaint_system.analytics.application.dto.response.ComplaintByStatusResponse;
import com.bag.complaint_system.analytics.application.dto.response.ComplaintByTypeResponse;
import com.bag.complaint_system.analytics.application.useCase.GetAverageResolutionTimeUseCase;
import com.bag.complaint_system.analytics.application.useCase.GetComplaintsByDateUseCase;
import com.bag.complaint_system.analytics.application.useCase.GetComplaintsByStatusUseCase;
import com.bag.complaint_system.analytics.application.useCase.GetComplaintsByTypeUseCase;
import com.bag.complaint_system.shared.infrastructure.security.SecurityContextHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

  private final GetComplaintsByDateUseCase getComplaintsByDateUseCase;
  private final GetComplaintsByStatusUseCase getComplaintsByStatusUseCase;
  private final GetComplaintsByTypeUseCase getComplaintsByTypeUseCase;
  private final GetAverageResolutionTimeUseCase getAverageResolutionTimeUseCase;
  private final SecurityContextHelper securityContextHelper;

  @GetMapping("/complaints-by-date")
  public ComplaintByDateRangeResponse getComplaintsByDate(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

    Long authId = securityContextHelper.getAuthenticatedUserId();
    return getComplaintsByDateUseCase.execute(authId, startDate, endDate);
  }

  @GetMapping("/complaints-by-type")
  public ComplaintByTypeResponse getComplaintsByType() {
    Long authId = securityContextHelper.getAuthenticatedUserId();
    return getComplaintsByTypeUseCase.execute(authId);
  }

  @GetMapping("/complaints-by-status")
  public ComplaintByStatusResponse getComplaintsByStatus() {
    Long authId = securityContextHelper.getAuthenticatedUserId();
    return getComplaintsByStatusUseCase.execute(authId);
  }

  @GetMapping("/average-resolution-time")
  public Double getAverageResolutionTime() {
    Long authId = securityContextHelper.getAuthenticatedUserId();
    return getAverageResolutionTimeUseCase.execute(authId);
  }
}
