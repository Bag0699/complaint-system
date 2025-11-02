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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetAllComplaintsUseCase {

  private final ComplaintRepository complaintRepository;
  private final UserRepository userRepository;
  private final ComplaintMapper complaintMapper;

  @Transactional(readOnly = true)
  public List<ComplaintResponse> execute(Long authId) {

    User authUser =
        userRepository
            .findById(authId)
            .orElseThrow(UserNotFoundException::new);

    if (!authUser.isAdmin()) {
      throw new RoleAccessDeniedException("Only admins can view all complaints");
    }

    List<Complaint> complaints = complaintRepository.findAll();

    if (complaints.isEmpty()) {
      return List.of();
    }

    List<Long> victimIds = complaints.stream().map(Complaint::getVictimId).distinct().toList();

    //    Map<Long, String> victimNamesMap =
    //        victimIds.stream()
    //            .collect(
    //                Collectors.toMap(
    //                    id -> id,
    //                    id -> {
    //                      try {
    //                        return userRepository
    //                            .findById(id)
    //                            .map(User::getFullName)
    //                            .orElse("Unknown User");
    //                      } catch (Exception e) {
    //                        return "Unknown User";
    //                      }
    //                    }));

    Map<Long, String> victimNamesMap =
        userRepository.findAllById(victimIds).stream()
            .collect(Collectors.toMap(User::getId, User::getFullName));

    List<String> victimNames =
        complaints.stream()
            .map(c -> victimNamesMap.getOrDefault(c.getVictimId(), "Unknow User"))
            .toList();

    return complaintMapper.toResponseList(complaints, victimNames);

    //    return complaints.stream()
    //        .map(
    //            complaint ->
    //                complaintMapper.toComplaintResponse(
    //                    complaint,
    //                    victimNamesMap.getOrDefault(complaint.getVictimId(), "Unknown User")))
    //        .collect(Collectors.toList());
  }
}
