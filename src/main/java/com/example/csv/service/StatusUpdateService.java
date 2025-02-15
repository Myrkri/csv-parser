package com.example.csv.service;

import com.example.csv.model.CsvRecord;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface StatusUpdateService {

    SseEmitter subscribe(String uuid);

    void sendUpdate(String uuid, CsvRecord record, boolean isEndOfFile);
}
