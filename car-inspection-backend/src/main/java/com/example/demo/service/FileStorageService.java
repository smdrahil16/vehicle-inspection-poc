package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileStorageService {

    private final S3Service s3Service;

    public FileStorageService(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    public String saveFile(MultipartFile file)
            throws IOException {

        return s3Service.uploadFile(file);
    }
}