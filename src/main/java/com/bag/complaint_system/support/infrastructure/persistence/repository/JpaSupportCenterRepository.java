package com.bag.complaint_system.support.infrastructure.persistence.repository;

import com.bag.complaint_system.support.infrastructure.persistence.entity.SupportCenterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaSupportCenterRepository extends JpaRepository<SupportCenterEntity, Long> {

  List<SupportCenterEntity> findByIsActive(Boolean isActive);

  List<SupportCenterEntity> findByDistrict(SupportCenterEntity.DistrictEntity district);

  List<SupportCenterEntity> findByDistrictAndIsActive(
      SupportCenterEntity.DistrictEntity district, Boolean isActive);
}
