package com.bag.complaint_system.support.infrastructure.persistence.adapter;

import com.bag.complaint_system.identity.domain.model.valueObject.Email;
import com.bag.complaint_system.identity.domain.model.valueObject.Phone;
import com.bag.complaint_system.support.domain.model.aggregate.SupportCenter;
import com.bag.complaint_system.support.domain.model.valueObject.Address;
import com.bag.complaint_system.support.domain.model.valueObject.District;
import com.bag.complaint_system.support.domain.model.valueObject.Schedule;
import com.bag.complaint_system.support.domain.repository.SupportCenterRepository;
import com.bag.complaint_system.support.infrastructure.persistence.entity.SupportCenterEntity;
import com.bag.complaint_system.support.infrastructure.persistence.repository.JpaSupportCenterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SupportCenterRepositoryAdapter implements SupportCenterRepository {

  private final JpaSupportCenterRepository jpaRepository;

  @Override
  public SupportCenter save(SupportCenter supportCenter) {
    SupportCenterEntity entity = toEntity(supportCenter);
    return toDomain(jpaRepository.save(entity));
  }

  @Override
  public Optional<SupportCenter> findById(Long id) {
    return jpaRepository.findById(id).map(this::toDomain);
  }

  @Override
  public List<SupportCenter> findAllActive() {
    return jpaRepository.findByIsActive(true).stream()
        .map(this::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public List<SupportCenter> findAll() {
    return jpaRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
  }

  @Override
  public List<SupportCenter> findByDistrict(District district) {
    SupportCenterEntity.DistrictEntity entityDistrict =
        SupportCenterEntity.DistrictEntity.valueOf(district.name());

    return jpaRepository.findByDistrict(entityDistrict).stream()
        .map(this::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public List<SupportCenter> findActiveByDistrict(District district) {
    SupportCenterEntity.DistrictEntity districtEntity =
        SupportCenterEntity.DistrictEntity.valueOf(district.name());
    return jpaRepository.findByDistrictAndIsActive(districtEntity, true).stream()
        .map(this::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public void deleteById(Long id) {
    if (!jpaRepository.existsById(id)) {
      throw new IllegalArgumentException("Support center not found with id: " + id);
    }
    jpaRepository.deleteById(id);
  }

  private SupportCenterEntity toEntity(SupportCenter center) {
    SupportCenterEntity entity = new SupportCenterEntity();
    entity.setId(center.getId());
    entity.setName(center.getName());
    entity.setStreet(center.getAddress().getStreet());
    entity.setDistrict(SupportCenterEntity.DistrictEntity.valueOf(center.getDistrict().name()));
    entity.setPhone(center.getPhone().getValue());
    entity.setEmail(center.getEmail().getValue());
    entity.setSchedule(center.getSchedule().getValue());
    entity.setIsActive(center.isActive());
    entity.setCreatedAt(center.getCreatedAt());
    entity.setUpdatedAt(center.getUpdatedAt());
    return entity;
  }

  private SupportCenter toDomain(SupportCenterEntity entity) {
    // Create value objects
    District district = District.valueOf(entity.getDistrict().name());
    Address address = new Address(entity.getStreet(), district);
    Phone phone = new Phone(entity.getPhone());
    Email email = new Email(entity.getEmail());
    Schedule schedule = new Schedule(entity.getSchedule());

    // Create a support center
    SupportCenter center = SupportCenter.create(entity.getName(), address, phone, email, schedule);

    center.setId(entity.getId());
    center.setCreatedAt(entity.getCreatedAt());
    center.setUpdatedAt(entity.getUpdatedAt());

    // Set active status
    if (!entity.getIsActive()) {
      center.deactivate();
    }
    return center;
  }
}
