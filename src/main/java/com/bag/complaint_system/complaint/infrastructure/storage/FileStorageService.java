package com.bag.complaint_system.complaint.infrastructure.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class FileStorageService {
    private final RestClient restClient;
    private final String bucketName;
    private final String supabaseStorageUrl;

    public FileStorageService(
            @Value("${supabase.url}") String supabaseUrl,
            @Value("${supabase.key}") String supabaseKey,
            @Value("${supabase.bucket}") String bucketName,
            RestClient.Builder restClientBuilder // Inyecta el Builder
    ) {
        this.bucketName = bucketName;
        // Construimos la URL base para la API de Storage
        this.supabaseStorageUrl = supabaseUrl + "/storage/v1/object";

        // Creamos un cliente RestClient pre-configurado
        this.restClient = restClientBuilder
                .baseUrl(this.supabaseStorageUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + supabaseKey)
                .build();
    }

    public String storeFile(MultipartFile file, Long complaintId) {
        // Validate file
        validateFile(file);

        try {
            // 2. Generar nombre de archivo
            String originalFilename = file.getOriginalFilename();
            String fileExtension = getFileExtension(originalFilename);
            String newFilename = complaintId + "_" + UUID.randomUUID() + fileExtension;
            String remotePath = complaintId + "/" + newFilename;

            // 3. Subir el archivo a Supabase Storage
            log.info("Uploading file to Supabase Storage at path: {}", remotePath);

            restClient.post()
                    .uri("/{bucket}/{path}", this.bucketName, remotePath)
                    .contentType(MediaType.parseMediaType(file.getContentType()))
                    .body(file.getResource()) // Envía el recurso del archivo
                    .retrieve() // Ejecuta la solicitud
                    .onStatus(status -> status.isError(), (request, response) -> {
                        String errorBody = response.getStatusText(); // Valor por defecto
                        try {
                            // Usamos getBody() y manejamos IOException
                            if (response.getBody() != null) {
                                errorBody = new String(response.getBody().readAllBytes());
                            }
                        } catch (IOException e) {
                            log.warn("Could not read error body from Supabase response", e);
                        }
                        throw new RuntimeException("Supabase storage error: " + errorBody);
                    })
                    .toBodilessEntity();

            log.info("File stored successfully in Supabase: {}", remotePath);

            // 4. Devolver la ruta relativa
            return remotePath;

        } catch (Exception ex) {
            log.error("Could not store file", ex);
            throw new RuntimeException("Could not store file: " + ex.getMessage(), ex);
        }
    }

    public Resource loadFile(String filePath) {
        try {
            log.info("Downloading file from Supabase: {}", filePath);

            // Llama a la API de Supabase para descargar el objeto
            byte[] fileBytes = restClient.get()
                    .uri("/{bucket}/{path}", this.bucketName, filePath)
                    .retrieve()
                    // Manejo de error si no se encuentra
                    .onStatus(status -> status.equals(HttpStatus.NOT_FOUND), (request, response) -> {
                        throw new RuntimeException("File not found in Supabase: " + filePath);
                    })
                    .body(byte[].class); // Descarga los bytes del archivo

            if (fileBytes == null) {
                throw new RuntimeException("File not found (empty response): " + filePath);
            }

            return new ByteArrayResource(fileBytes);

        } catch (Exception ex) {
            log.error("File not found: {}", filePath, ex);
            throw new RuntimeException("File not found: " + filePath, ex);
        }
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Cannot upload empty file");
        }

        String filename = file.getOriginalFilename();
        if (filename == null || filename.contains("..")) {
            throw new IllegalArgumentException("Invalid file name: " + filename);
        }

        // Validate file type
        String contentType = file.getContentType();
        if (contentType == null || !isAllowedFileType(contentType)) {
            throw new IllegalArgumentException(
                    "File type not allowed. Allowed types: images, PDF, documents"
            );
        }

        // Validate file size (max 5MB)
        long maxSize = 5 * 1024 * 1024; // 5MB
        if (file.getSize() > maxSize) {
            throw new IllegalArgumentException("File size cannot exceed 5MB");
        }
    }

    private boolean isAllowedFileType(String contentType) {
        return contentType.equals("image/jpeg") ||
                contentType.equals("image/png") ||
                contentType.equals("image/jpg") ||
                contentType.equals("application/pdf") ||
                contentType.equals("application/msword") ||
                contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
    }

    private String getFileExtension(String filename) {
        if (filename == null) {
            return "";
        }
        int dotIndex = filename.lastIndexOf('.');
        return dotIndex > 0 ? filename.substring(dotIndex) : "";
    }

}
