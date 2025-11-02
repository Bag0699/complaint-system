package com.bag.complaint_system.support.application.useCase;

import com.bag.complaint_system.identity.domain.model.aggregate.User;
import com.bag.complaint_system.identity.domain.model.valueObject.Email;
import com.bag.complaint_system.identity.domain.model.valueObject.Phone;
import com.bag.complaint_system.identity.domain.repository.UserRepository;
import com.bag.complaint_system.shared.domain.exception.InvalidDistrictException;
import com.bag.complaint_system.shared.domain.exception.RoleAccessDeniedException;
import com.bag.complaint_system.shared.domain.exception.SupportCenterNotFoundException;
import com.bag.complaint_system.shared.domain.exception.UserNotFoundException;
import com.bag.complaint_system.support.application.dto.request.UpdateSupportCenterRequest;
import com.bag.complaint_system.support.application.dto.response.SupportCenterResponse;
import com.bag.complaint_system.support.application.mapper.SupportCenterMapper;
import com.bag.complaint_system.support.domain.model.aggregate.SupportCenter;
import com.bag.complaint_system.support.domain.model.valueObject.Address;
import com.bag.complaint_system.support.domain.model.valueObject.District;
import com.bag.complaint_system.support.domain.model.valueObject.Schedule;
import com.bag.complaint_system.support.domain.repository.SupportCenterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateSupportCenterUseCase {
  private final SupportCenterRepository supportCenterRepository;
  private final UserRepository userRepository;
  private final SupportCenterMapper mapper;

  @Transactional
  public SupportCenterResponse execute(
      Long centerId, Long authId, UpdateSupportCenterRequest request) {

    User userAuth = userRepository.findById(authId).orElseThrow(UserNotFoundException::new);

    if (!userAuth.isAdmin()) {
      throw new RoleAccessDeniedException("Only admins can update support centers");
    }

    SupportCenter center =
        supportCenterRepository.findById(centerId).orElseThrow(SupportCenterNotFoundException::new);

    District district;
    try {
      district = District.valueOf(request.getDistrict().toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new InvalidDistrictException();
    }

    Address address = new Address(request.getStreet(), district);
    Phone phone = new Phone(request.getPhone());
    Email email = new Email(request.getEmail());
    Schedule schedule = new Schedule(request.getSchedule());

    center.update(request.getName(), address, phone, email, schedule);
    return mapper.toResponse(supportCenterRepository.save(center));
  }
}
