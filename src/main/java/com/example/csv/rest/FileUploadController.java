package com.example.csv.rest;

import com.example.csv.model.CsvRecord;
import com.example.csv.model.enums.ProcessingStatus;
import com.example.csv.service.FileUpload;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/file")
@AllArgsConstructor
public class FileUploadController {

    private final FileUpload fileUpload;

    @PostMapping("/upload")
    public Map<String, ProcessingStatus> upload(@RequestPart(value = "file") MultipartFile file) {
        return fileUpload.upload(file);
    }

}
