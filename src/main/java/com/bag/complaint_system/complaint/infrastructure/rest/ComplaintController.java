package com.bag.complaint_system.complaint.infrastructure.rest;

import com.bag.complaint_system.complaint.application.dto.request.CreateComplaintRequest;
import com.bag.complaint_system.complaint.application.dto.request.UpdateComplaintStatusRequest;
import com.bag.complaint_system.complaint.application.dto.response.ComplaintDetailResponse;
import com.bag.complaint_system.complaint.application.dto.response.ComplaintResponse;
import com.bag.complaint_system.complaint.application.dto.response.EvidenceResponse;
import com.bag.complaint_system.complaint.application.useCase.*;
import com.bag.complaint_system.shared.infrastructure.security.SecurityContextHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/complaints")
@RequiredArgsConstructor
public class ComplaintController {

  private final CreateComplaintUseCase createComplaintUseCase;
  private final GetComplaintsByVictimUseCase getComplaintsByVictimUseCase;
  private final GetComplaintDetailUseCase getComplaintDetailUseCase;
  private final GetAllComplaintsUseCase getAllComplaintsUseCase;
  private final UpdateComplaintStatusUseCase updateComplaintStatusUseCase;
  private final AddEvidenceUseCase addEvidenceUseCase;

  private final SecurityContextHelper securityContextHelper;

  // Para crear una denuncia - solo víctima
  @PostMapping()
  public ResponseEntity<ComplaintDetailResponse> createComplaint(
      @Valid @RequestBody CreateComplaintRequest request) {

    Long victimId = securityContextHelper.getAuthenticatedUserId();
    ComplaintDetailResponse response = createComplaintUseCase.execute(victimId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  // Para obtener todas las denuncias - solo víctima
  @GetMapping("/my-complaints")
  public List<ComplaintResponse> getMyComplaints() {
    Long victimId = securityContextHelper.getAuthenticatedUserId();
    return getComplaintsByVictimUseCase.execute(victimId);
  }

  // Para obtener todas las denuncias - solo admin
  @GetMapping
  public List<ComplaintResponse> getAllComplaints() {
    Long authenticatedUserId = securityContextHelper.getAuthenticatedUserId();
    return getAllComplaintsUseCase.execute(authenticatedUserId);
  }

  // Para obtener detalles de una denuncia por ID
  @GetMapping("/{id}")
  public ComplaintDetailResponse getComplaintDetail(@PathVariable Long id) {
    Long victimId = securityContextHelper.getAuthenticatedUserId();
    return getComplaintDetailUseCase.execute(id, victimId);
  }

  // Para actualizar el estado de una denuncia - solo admin
  @PatchMapping("/{id}/status")
  public ComplaintDetailResponse updateComplaintStatus(
      @PathVariable Long id, @Valid @RequestBody UpdateComplaintStatusRequest request) {
    Long authenticatedUserId = securityContextHelper.getAuthenticatedUserId();
    return updateComplaintStatusUseCase.execute(id, authenticatedUserId, request);
  }

  // Para agregar una evidencia a una denuncia - solo la víctima dueña
  @PostMapping(value = "/{id}/evidence", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public EvidenceResponse addEvidence(
      @PathVariable Long id, @RequestParam("file") MultipartFile file) {

    Long authUserId = securityContextHelper.getAuthenticatedUserId();
    return addEvidenceUseCase.execute(id, authUserId, file);
  }
}
