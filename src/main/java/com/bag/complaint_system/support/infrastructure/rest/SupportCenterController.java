package com.bag.complaint_system.support.infrastructure.rest;

import com.bag.complaint_system.shared.infrastructure.security.SecurityContextHelper;
import com.bag.complaint_system.support.application.dto.request.CreateSupportCenterRequest;
import com.bag.complaint_system.support.application.dto.request.UpdateSupportCenterRequest;
import com.bag.complaint_system.support.application.dto.response.SupportCenterResponse;
import com.bag.complaint_system.support.application.useCase.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/support-centers")
@RequiredArgsConstructor
public class SupportCenterController {

  private final CreateSupportCenterUseCase createSupportCenterUseCase;
  private final UpdateSupportCenterUseCase updateSupportCenterUseCase;
  private final DeleteSupportCenterUseCase deleteSupportCenterUseCase;
  private final GetAllSupportCentersUseCase getAllSupportCentersUseCase;
  //    private final GetSupportCenterByIdUseCase getSupportCenterByIdUseCase;
  private final GetRecommendationsUseCase getRecommendationsUseCase;
  private final SecurityContextHelper securityContextHelper;

  @PostMapping("/create")
  public ResponseEntity<SupportCenterResponse> createSupportCenter(
      @Valid @RequestBody CreateSupportCenterRequest request) {

    Long authenticatedUserId = securityContextHelper.getAuthenticatedUserId();
    SupportCenterResponse response =
        createSupportCenterUseCase.execute(authenticatedUserId, request);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  // Método para actualizar un centro de ayuda
  @PutMapping("/{id}/edit")
  public SupportCenterResponse updateSupportCenter(
      @PathVariable Long id, @Valid @RequestBody UpdateSupportCenterRequest request) {

    Long authenticatedUserId = securityContextHelper.getAuthenticatedUserId();
    return updateSupportCenterUseCase.execute(id, authenticatedUserId, request);
  }

  @GetMapping
  public List<SupportCenterResponse> getAllSupportCenters() {
    return getAllSupportCentersUseCase.execute();
  }

  @GetMapping("/recommendations/{district}")
  public List<SupportCenterResponse> getRecommendationsByDistrict(@PathVariable String district) {
    return getRecommendationsUseCase.execute(district);
  }

  @DeleteMapping("/{id}/delete")
  public ResponseEntity<Void> deleteSupportCenter(@PathVariable Long id) {
    Long authenticatedUserId = securityContextHelper.getAuthenticatedUserId();
    deleteSupportCenterUseCase.execute(id, authenticatedUserId);
    return ResponseEntity.noContent().build();
  }
}
