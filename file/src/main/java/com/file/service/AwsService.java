package com.file.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.util.UUID;

@Service
public class AwsService {

    private final S3Client s3Client; 

    @Value("${aws.s3.bucket-name}")
    private String bucketName;


    public AwsService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(File file) {
        try {
            String key = UUID.randomUUID() + "_" + file.getName();

            s3Client.putObject(PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build(), RequestBody.fromFile(file));

            return "https://" + bucketName + ".s3.amazonaws.com/" + key;
        } catch (Exception e) {
            throw new RuntimeException("File upload to S3 failed: " + e.getMessage());
        }
    }
    
    
    public void deleteFile(String key) {
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build());
        System.out.println("File with key " + key + " deleted successfully from S3.");
    }
    
}
