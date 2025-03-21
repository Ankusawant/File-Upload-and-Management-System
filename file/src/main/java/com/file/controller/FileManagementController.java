package com.file.controller;

import com.file.entity.FileManagement;
import com.file.service.FileManagementService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileManagementController {

    @Autowired
    private FileManagementService fileManagementService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) {
        try {
            String fileUrl = fileManagementService.uploadFile(file);
            return ResponseEntity.ok(fileUrl);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("File upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFile(@PathVariable long id) {
        try {
            return ResponseEntity.ok(fileManagementService.getFileDetails(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to retrieve file: " + e.getMessage());
        }
    }
    
    
    @DeleteMapping("/deletefile/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable long id) {
        try {
            fileManagementService.deleteFile(id);
            return ResponseEntity.status(HttpStatus.OK).body("File deleted successfully from S3 and database.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while deleting the file.");
        }
    }
    
    
}
