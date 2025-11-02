package com.bag.complaint_system.support.application.useCase;

import com.bag.complaint_system.support.application.dto.response.SupportCenterResponse;
import com.bag.complaint_system.support.application.mapper.SupportCenterMapper;
import com.bag.complaint_system.support.domain.model.aggregate.SupportCenter;
import com.bag.complaint_system.support.domain.repository.SupportCenterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllSupportCentersUseCase {

  private final SupportCenterRepository supportCenterRepository;
  private final SupportCenterMapper mapper;

  @Transactional(readOnly = true)
  public List<SupportCenterResponse> execute() {
    List<SupportCenter> centers = supportCenterRepository.findAll();

    return mapper.toResponseList(centers);
  }
}
