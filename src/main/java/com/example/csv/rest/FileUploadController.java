package com.example.csv.rest;

import com.example.csv.model.CsvRecord;
import com.example.csv.service.FileUpload;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class FileUploadController {

    private final FileUpload fileUpload;

    @PostMapping("/upload")
    public List<CsvRecord> upload(@RequestPart(value = "file") MultipartFile file) {
        return fileUpload.upload(file);
    }

}
