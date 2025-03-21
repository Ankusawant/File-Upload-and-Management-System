package com.file.service;

import com.file.entity.FileManagement;
import com.file.repo.FileManagementRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class FileManagementService {

     @Autowired
     private FileManagementRepo fileManagementRepo;

     @Autowired
    private AwsService fileManagementAwsService;

    @Autowired
    private S3Client s3Client; 

    private final String BUCKET_NAME = "//{Enter your bucket name}";

    @Transactional
    public String uploadFile(MultipartFile file) throws Exception {
        Path tempPath = Files.createTempFile("upload_", file.getOriginalFilename());
        Files.copy(file.getInputStream(), tempPath, StandardCopyOption.REPLACE_EXISTING);

        String fileUrl = fileManagementAwsService.uploadFile(tempPath.toFile());

        FileManagement metadata = new FileManagement();
        metadata.setFileName(file.getOriginalFilename());
        metadata.setFileUrl(fileUrl);
        metadata.setSize(file.getSize());
        metadata.setUploadDate(LocalDateTime.now());

        fileManagementRepo.save(metadata);
        return fileUrl;
    }

    @Cacheable("fileDetails")
    public FileManagement getFileDetails(Long id) {
        return fileManagementRepo.findById(id).orElseThrow(() -> 
            new RuntimeException("File not found with ID: " + id));
    }
    public void deleteFile(long id) {
        Optional<FileManagement> fileManagementOptional = fileManagementRepo.findById(id);

        if (fileManagementOptional.isPresent()) {
            FileManagement fileManagement = fileManagementOptional.get();
            String fileUrl = fileManagement.getFileUrl();  

   
            String fileKey = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);

      
            fileManagementAwsService.deleteFile(fileKey);

           
            fileManagementRepo.deleteById(id);
        } else {
            throw new RuntimeException("File not found with id: " + id);
        }
    }
}