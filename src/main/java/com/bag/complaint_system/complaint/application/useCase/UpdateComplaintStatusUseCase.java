package com.bag.complaint_system.complaint.application.useCase;

import com.bag.complaint_system.complaint.application.dto.request.UpdateComplaintStatusRequest;
import com.bag.complaint_system.complaint.application.dto.response.ComplaintDetailResponse;
import com.bag.complaint_system.complaint.application.mapper.ComplaintMapper;
import com.bag.complaint_system.complaint.domain.event.ComplaintStatusChangedEvent;
import com.bag.complaint_system.complaint.domain.model.aggregate.Complaint;
import com.bag.complaint_system.complaint.domain.model.valueObject.ComplaintStatus;
import com.bag.complaint_system.complaint.domain.repository.ComplaintRepository;
import com.bag.complaint_system.identity.domain.model.aggregate.User;
import com.bag.complaint_system.identity.domain.repository.UserRepository;
import com.bag.complaint_system.shared.domain.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import static com.bag.complaint_system.shared.util.ErrorCatalog.INSUFFICIENT_ADMIN_ROLE;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateComplaintStatusUseCase {

  private final ComplaintRepository complaintRepository;
  private final UserRepository userRepository;
  private final ComplaintMapper complaintMapper;
  private final ApplicationEventPublisher eventPublisher;

  public ComplaintDetailResponse execute(
      Long complaintId, Long authUserId, UpdateComplaintStatusRequest request) {

    User authUser = userRepository.findById(authUserId).orElseThrow(UserNotFoundException::new);

    if (!authUser.isAdmin()) {
      throw new RoleAccessDeniedException(INSUFFICIENT_ADMIN_ROLE.getMessage());
    }

    Complaint complaint =
        complaintRepository.findById(complaintId).orElseThrow(ComplaintNotFoundException::new);

    User victim =
        userRepository.findById(complaint.getVictimId()).orElseThrow(VictimNotFoundException::new);

    ComplaintStatus newStatus;

    try {
      newStatus = ComplaintStatus.valueOf(request.getNewStatus().toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new InvalidValueException();
    }

    ComplaintStatus previousStatus = complaint.getStatus();

    complaint.updateStatus(newStatus);

    Complaint updatedComplaint = complaintRepository.save(complaint);

    log.info(
        "Complaint status updated from {} to {} for complaint {}",
        previousStatus,
        newStatus,
        complaintId);

    //    ComplaintStatusChangedEvent event =
    //        ComplaintStatusChangedEvent.create(
    //            updatedComplaint.getId(),
    //            victim.getId(),
    //            victim.getEmail().getValue(),
    //            previousStatus,
    //            newStatus);
    //
    //    eventPublisher.publishEvent(event);
    //
    return complaintMapper.toDetailResponse(
        updatedComplaint, victim.getFullName(), victim.getEmail().getValue());
  }
}
