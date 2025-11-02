package com.bag.complaint_system.complaint.application.useCase;

import com.bag.complaint_system.complaint.application.dto.response.ComplaintResponse;
import com.bag.complaint_system.complaint.application.mapper.ComplaintMapper;
import com.bag.complaint_system.complaint.domain.model.aggregate.Complaint;
import com.bag.complaint_system.complaint.domain.repository.ComplaintRepository;
import com.bag.complaint_system.identity.domain.model.aggregate.User;
import com.bag.complaint_system.identity.domain.repository.UserRepository;
import com.bag.complaint_system.shared.domain.exception.RoleAccessDeniedException;
import com.bag.complaint_system.shared.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetComplaintsByVictimUseCase {

  private final ComplaintRepository complaintRepository;
  private final UserRepository userRepository;
  private final ComplaintMapper complaintMapper;

  public List<ComplaintResponse> execute(Long victimId) {
    User userAuth =
        userRepository
            .findById(victimId)
            .orElseThrow(UserNotFoundException::new);

    if (!userAuth.isVictim()) {
      throw new RoleAccessDeniedException("Only victims can view their own complaints");
    }
    List<Complaint> complaints = complaintRepository.findByVictimId(victimId);

    if (complaints.isEmpty()) {
      return List.of();
    }

    return complaints.stream()
        .map(complaint -> complaintMapper.toComplaintResponse(complaint, userAuth.getFullName()))
        .collect(Collectors.toList());
  }
}
