package com.example.csv.service;

import com.example.csv.model.enums.ProcessingStatus;

public interface StatusStorageService {
    ProcessingStatus getProcessingStatus(final String id);

    void incrementProcessingStatus(final String id);
}
