package com.bag.complaint_system.complaint.application.useCase;

import com.bag.complaint_system.complaint.application.dto.response.ComplaintDetailResponse;
import com.bag.complaint_system.complaint.application.mapper.ComplaintMapper;
import com.bag.complaint_system.complaint.domain.model.aggregate.Complaint;
import com.bag.complaint_system.complaint.domain.repository.ComplaintRepository;
import com.bag.complaint_system.identity.domain.model.aggregate.User;
import com.bag.complaint_system.identity.domain.repository.UserRepository;
import com.bag.complaint_system.shared.domain.exception.ComplaintNotFoundException;
import com.bag.complaint_system.shared.domain.exception.RoleAccessDeniedException;
import com.bag.complaint_system.shared.domain.exception.UserNotFoundException;
import com.bag.complaint_system.shared.domain.exception.VictimNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetComplaintDetailUseCase {

  private final ComplaintRepository complaintRepository;
  private final UserRepository userRepository;
  private final ComplaintMapper complaintMapper;

  @Transactional(readOnly = true)
  public ComplaintDetailResponse execute(Long complaintId, Long authId) {

      Complaint complaint = complaintRepository.findById(complaintId)
              .orElseThrow(ComplaintNotFoundException::new);

      User userAuth = userRepository.findById(authId)
              .orElseThrow(UserNotFoundException::new);

      boolean isOwner = complaint.belongsToVictim(authId);
      boolean isAdmin = userAuth.isAdmin();

      if (!isOwner && !isAdmin) {
      throw new RoleAccessDeniedException("You can only view your own complaints");
      }

      User victim = userRepository.findById(complaint.getVictimId())
              .orElseThrow(VictimNotFoundException::new);

      return complaintMapper.toDetailResponse(
              complaint,
              victim.getFullName(),
              victim.getEmail().getValue()
      );
  }
}
