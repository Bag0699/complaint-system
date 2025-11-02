package com.bag.complaint_system.complaint.application.useCase;

import com.bag.complaint_system.complaint.application.dto.request.CreateComplaintRequest;
import com.bag.complaint_system.complaint.application.dto.response.ComplaintDetailResponse;
import com.bag.complaint_system.complaint.application.mapper.ComplaintMapper;
import com.bag.complaint_system.complaint.domain.model.aggregate.Complaint;
import com.bag.complaint_system.complaint.domain.model.entity.Aggressor;
import com.bag.complaint_system.complaint.domain.model.valueObject.VictimRelationship;
import com.bag.complaint_system.complaint.domain.model.valueObject.ViolenceType;
import com.bag.complaint_system.complaint.domain.repository.ComplaintRepository;
import com.bag.complaint_system.identity.domain.model.aggregate.User;
import com.bag.complaint_system.identity.domain.repository.UserRepository;
import com.bag.complaint_system.shared.domain.exception.RoleAccessDeniedException;
import com.bag.complaint_system.shared.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateComplaintUseCase {

  private final ComplaintRepository complaintRepository;
  private final UserRepository userRepository;
  private final ComplaintMapper complaintMapper;

  @Transactional
  public ComplaintDetailResponse execute(Long victimId, CreateComplaintRequest request) {

    User victim = userRepository.findById(victimId).orElseThrow(UserNotFoundException::new);

    if (!victim.isVictim()) {
      throw new RoleAccessDeniedException("Only victims can create complaints");
    }

    // Parse enums
    ViolenceType violenceType = ViolenceType.valueOf(request.getViolenceType().toUpperCase());
    VictimRelationship relationship =
        VictimRelationship.valueOf(request.getAggressorRelationship().toUpperCase());

    // Create aggressor
    Aggressor aggressor =
        Aggressor.create(
            request.getAggressorFullName(), relationship, request.getAggressorAdditionalDetails());

    // Create complaint
    Complaint complaint =
        Complaint.create(
            victimId,
            request.getDescription(),
            violenceType,
            request.getIncidentDate(),
            request.getIncidentLocation(),
            aggressor);

    // Save complaint
    Complaint savedComplaint = complaintRepository.save(complaint);

    // Map to response
    return complaintMapper.toDetailResponse(
        savedComplaint, victim.getFullName(), victim.getEmail().getValue());
  }
}
