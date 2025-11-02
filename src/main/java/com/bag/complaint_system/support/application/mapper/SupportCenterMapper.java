package com.bag.complaint_system.support.application.mapper;

import com.bag.complaint_system.support.application.dto.response.SupportCenterResponse;
import com.bag.complaint_system.support.domain.model.aggregate.SupportCenter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SupportCenterMapper {

  @Mapping(target = "street", expression = "java(center.getAddress().getStreet())")
  @Mapping(target = "district", expression = "java(center.getAddress().getDistrict().name())")
  @Mapping(target = "fullAddress", expression = "java(center.getAddress().getFullAddress())")
  @Mapping(target = "phone", expression = "java(center.getPhone().getValue())")
  @Mapping(target = "email", expression = "java(center.getEmail().getValue())")
  @Mapping(target = "schedule", expression = "java(center.getSchedule().getValue())")
  @Mapping(target = "isActive", expression = "java(center.isActive())")
  SupportCenterResponse toResponse(SupportCenter center);

    List<SupportCenterResponse> toResponseList(List<SupportCenter> centers);
}
