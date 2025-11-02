package com.bag.complaint_system.support.application.useCase;

import com.bag.complaint_system.shared.domain.exception.InvalidDistrictException;
import com.bag.complaint_system.support.application.dto.response.SupportCenterResponse;
import com.bag.complaint_system.support.application.mapper.SupportCenterMapper;
import com.bag.complaint_system.support.domain.model.aggregate.SupportCenter;
import com.bag.complaint_system.support.domain.model.valueObject.District;
import com.bag.complaint_system.support.domain.repository.SupportCenterRepository;
import com.bag.complaint_system.support.domain.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetRecommendationsUseCase {

  private final SupportCenterRepository supportCenterRepository;
  private final SupportCenterMapper mapper;

  public List<SupportCenterResponse> execute(String districtName) {
    District district;
    try {
      district = District.valueOf(districtName.toUpperCase().replace(" ", "_"));
    } catch (IllegalArgumentException e) {
      throw new InvalidDistrictException();
    }

    RecommendationService recommendationService =
        new RecommendationService(supportCenterRepository);

    return mapper.toResponseList(recommendationService.getRecommendationsByDistrict(district));
  }
}
