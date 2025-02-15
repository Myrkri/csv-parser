package com.example.csv.service;

import com.example.csv.model.enums.ProcessingStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


public interface FileUpload {

    Map<String, ProcessingStatus> upload(MultipartFile file);

    String uploadAsync(final MultipartFile file);
}
