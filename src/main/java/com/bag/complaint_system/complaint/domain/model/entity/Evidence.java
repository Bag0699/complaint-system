package com.bag.complaint_system.complaint.domain.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Evidence {

    private Long id;
    private String fileName;
    private String filePath;
    private String fileType;
    private Long fileSize;
    private LocalDateTime uploadedAt;

    private Evidence() {}

    public static Evidence create(
            String fileName,
            String filePath,
            String fileType,
            Long fileSize
    ){
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("File name cannot be empty.");
        }
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be empty.");
        }
        if (fileSize == null || fileSize < 0) {
            throw new IllegalArgumentException("File size must be a non-negative number.");
        }
        if (fileType == null || fileType.trim().isEmpty()) {
            throw new IllegalArgumentException("File type cannot be empty.");
        }
        if (!fileType.matches("^[a-zA-Z0-9/._-]+$")) {
            throw new IllegalArgumentException("Invalid file type format: " + fileType);
        }

        if (fileSize > 50_000_000) {
            throw new IllegalArgumentException("File size exceeds the allowed limit of 50MB.");
        }

        Evidence evidence = new Evidence();
        evidence.fileName = fileName;
        evidence.filePath = filePath;
        evidence.fileType = fileType;
        evidence.fileSize = fileSize;
        evidence.uploadedAt = LocalDateTime.now();
        return evidence;
    }
}
