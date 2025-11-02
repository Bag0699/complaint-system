package com.bag.complaint_system.complaint.application.mapper;

import com.bag.complaint_system.complaint.application.dto.response.EvidenceResponse;
import com.bag.complaint_system.complaint.domain.model.entity.Evidence;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EvidenceMapper {

  EvidenceResponse toEvidenceResponse(Evidence evidence);
}
