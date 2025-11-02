package com.bag.complaint_system.support.domain.service;

import com.bag.complaint_system.support.domain.model.aggregate.SupportCenter;
import com.bag.complaint_system.support.domain.model.valueObject.District;
import com.bag.complaint_system.support.domain.repository.SupportCenterRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RecommendationService {

  private final SupportCenterRepository supportCenterRepository;

  public RecommendationService(SupportCenterRepository supportCenterRepository) {
    this.supportCenterRepository = supportCenterRepository;
  }

  public List<SupportCenter> getRecommendationsByDistrict(District district) {
    return supportCenterRepository.findByDistrict(district).stream()
        .sorted(Comparator.comparing(SupportCenter::getName))
        .collect(Collectors.toList());
  }
}
