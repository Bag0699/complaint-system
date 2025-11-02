package com.bag.complaint_system.complaint.application.useCase;

import com.bag.complaint_system.complaint.application.dto.response.EvidenceResponse;
import com.bag.complaint_system.complaint.application.mapper.EvidenceMapper;
import com.bag.complaint_system.complaint.domain.model.aggregate.Complaint;
import com.bag.complaint_system.complaint.domain.model.entity.Evidence;
import com.bag.complaint_system.complaint.domain.repository.ComplaintRepository;
import com.bag.complaint_system.complaint.infrastructure.storage.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddEvidenceUseCase {

  private final ComplaintRepository complaintRepository;
  private final EvidenceMapper evidenceMapper;
  private final FileStorageService fileStorageService;

  @Transactional
  public EvidenceResponse execute(Long complaintId, Long authUserId, MultipartFile file) {

    if (file == null || file.isEmpty()) {
      throw new IllegalArgumentException("File is required");
    }

    Complaint complaint =
        complaintRepository
            .findById(complaintId)
            .orElseThrow(
                () -> new IllegalArgumentException("Complaint not found with id: " + complaintId));

    if (!complaint.belongsToVictim(authUserId)) {
      throw new IllegalArgumentException("You can only add evidence to your own complaints");
    }

    if (complaint.isClosed()) {
      throw new IllegalArgumentException("Cannot add evidence to a closed complaint");
    }

    log.info("Storing file for complaint {}: {}", complaintId, file.getOriginalFilename());

    String fileName = file.getOriginalFilename();
    String filePath = fileStorageService.storeFile(file, complaintId);
    String fileType = file.getContentType();
    Long fileSize = file.getSize();

    Evidence evidence = Evidence.create(fileName, filePath, fileType, fileSize);

    complaint.addEvidence(evidence);

    Complaint updatedComplaint = complaintRepository.save(complaint);

    log.info("Evidence added successfully to complaint {}", complaintId);

    Evidence savedEvidence = updatedComplaint.getEvidences().getLast();

    // Esto está a sujeto a cambio
    // evidenceMapper.toEvidenceUploadResponse(savedEvidence, complaintId);
    return evidenceMapper.toEvidenceResponse(savedEvidence);
  }
}
