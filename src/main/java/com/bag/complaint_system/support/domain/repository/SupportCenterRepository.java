package com.bag.complaint_system.support.domain.repository;

import com.bag.complaint_system.support.domain.model.aggregate.SupportCenter;
import com.bag.complaint_system.support.domain.model.valueObject.District;

import java.util.List;
import java.util.Optional;

public interface SupportCenterRepository {

  SupportCenter save(SupportCenter supportCenter);

  Optional<SupportCenter> findById(Long id);

  List<SupportCenter> findAllActive();

  List<SupportCenter> findAll();

  List<SupportCenter> findByDistrict(District district);

  List<SupportCenter> findActiveByDistrict(District district);

  void deleteById(Long id);
}
