package com.example.csv.rest;

import com.example.csv.model.enums.ProcessingStatus;
import com.example.csv.service.FileUpload;
import com.example.csv.service.StatusUpdateService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/file")
@AllArgsConstructor
public class FileUploadController {

    private final FileUpload fileUpload;
    private final StatusUpdateService statusUpdateService;

    @PostMapping("/upload")
    public Map<String, ProcessingStatus> upload(@RequestPart(value = "file") MultipartFile file) {
        return fileUpload.upload(file);
    }

    @PostMapping("/upload-stream")
    public ResponseEntity<SseEmitter> uploadStream(@RequestPart(value = "file") MultipartFile file) {
        String uuid = fileUpload.uploadAsync(file);
        SseEmitter emitter = statusUpdateService.subscribe(uuid);

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(emitter);
    }

}
