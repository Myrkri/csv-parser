package com.example.csv.service;

import com.example.csv.model.enums.ProcessingStatus;
import com.example.csv.repository.entity.StatusStorageEntity;

public interface StatusStorageService {
    ProcessingStatus getProcessingStatus(final String id);

    void incrementProcessingStatus(final String id, boolean isEndOfFile);

    void create(StatusStorageEntity entity);
}
