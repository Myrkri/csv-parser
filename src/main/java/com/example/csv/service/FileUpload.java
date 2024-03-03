package com.example.csv.service;

import com.example.csv.model.CsvRecord;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface FileUpload {

    List<CsvRecord> upload(MultipartFile file);

}
