package com.example.csv.rest;

import com.example.csv.model.enums.ProcessingStatus;
import com.example.csv.service.StatusStorageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class StatusStorageController {

    private final StatusStorageService statusStorageService;

    @GetMapping("/get-upload-status/{id}")
    public ProcessingStatus getProcessingStatus(@PathVariable String id) {
        return statusStorageService.getProcessingStatus(id);
    }
}
