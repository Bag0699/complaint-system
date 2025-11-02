package com.bag.complaint_system.complaint.application.mapper;

import com.bag.complaint_system.complaint.application.dto.response.AggressorResponse;
import com.bag.complaint_system.complaint.domain.model.entity.Aggressor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AggressorMapper {

  AggressorResponse toAggressorResponse(Aggressor aggressor);
}
